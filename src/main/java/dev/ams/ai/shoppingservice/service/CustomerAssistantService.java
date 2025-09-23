package dev.ams.ai.shoppingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ams.ai.shoppingservice.dto.ChatRequest;
import dev.ams.ai.shoppingservice.dto.ChatResponse;
import dev.ams.ai.shoppingservice.dto.RecommendationRequest;
import dev.ams.ai.shoppingservice.dto.RecommendationResponse;
import dev.ams.ai.shoppingservice.entity.Customer;
import dev.ams.ai.shoppingservice.repository.CustomerRepository;
import dev.ams.ai.shoppingservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerAssistantService {
    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CustomerRepository customerRepo;
    private final OrderRepository orderRepo;

    public ChatResponse handleChat(ChatRequest request) {
        log.info("Processing chat request: {}", request);

        Optional<Customer> customer = customerRepo.findById(request.getCustomerId());
        String userContext = "";
        if(customer.isPresent()){
            Customer c = customer.get();
            userContext = String.format(
                    "%s is a %d-year-old %s, with customerId %d.\n",
                    c.getName(),
                    c.getAge(),
                    c.getGender().name().toLowerCase(),
                    c.getId()
            );
            userContext += enrichCustomerContext(c);
        }

        String query = userContext + "\n" + request.getMessage();
        // Retrieve related documents from VectorStore
        List<Document> relevantDocs =
                vectorStore.similaritySearch(
                        SearchRequest.builder()
                                .query(query)
                                .topK(5)
                                .similarityThreshold(0.5)
                                .build());

        // Build context from retrieved documents
        StringBuilder contextBuilder = new StringBuilder();
        for (Document doc : relevantDocs) {
            contextBuilder.append(doc.getFormattedContent()).append("\n");
        }
        String context = contextBuilder.toString();

        // Augment user query with context
        String ragPrompt = """
            You are a helpful shopping assistant.
                Use the following context and user information to answer the customer's question:
                Context:
                %s
            
                User context: %s
            
                Question:
                %s
            """.formatted(context, userContext, request.getMessage());

        // Call the LLM via ChatClient
        String response = chatClient.prompt()
                .user(ragPrompt)
                .call()
                .content();
        log.debug("Generated chat response: {}", response);
        return new ChatResponse(response);
    }

    public RecommendationResponse handleRecommendation(RecommendationRequest request) throws JsonProcessingException {
        log.info("Processing recommendation request: {}", request);
        String prompt = String.format(
                "Based on customer age %d, gender %s, and previous purchases, recommend products. " +
                "Available categories: Electronics, Fashion, Home, Books. " +
                "Provide up to 3 products recommendations with reasons." +
                "Respond with a valid JSON array of objects with the following structure: " +
                "[{\"title\": \"Product 1\", \"category\": \"Category 1\", \"notes\": \"reason 1\"}, ...] " +
                "Only respond with the raw JSON array without any additional text or formatting." +
                "Do not include any markdown formatting or backticks in the response.",
                request.getAge(), request.getGender()
        );

        String response = chatClient.prompt(prompt).call().content();
        log.debug("Generated recommendations: {}", response);

        // Clean and format the response
        String cleanResponse = response
                .replaceAll("```(json)?", "")  // Remove markdown code blocks
                .replaceAll("^\\s+|\\s+$", "") // Trim whitespace
                .replaceAll("\\n", "");        // Remove newlines

        String jsonResponse = "{\"recommendedProducts\":" + cleanResponse + "}";
        log.debug("Formatted JSON: {}", jsonResponse);

        return objectMapper.readValue(jsonResponse, RecommendationResponse.class);
    }

    private String enrichCustomerContext(Customer customer) {
        // Add richer, descriptive text for better embeddings
        StringBuilder sb = new StringBuilder();
        sb.append("Purchased products: ");
        orderRepo.findByCustomerId(customer.getId()).forEach(o -> {
            o.getItems().forEach(item -> {
                sb.append(item.getProduct().getTitle())
                        .append(", ");
            });
        });
        return sb.toString();
    }
}
