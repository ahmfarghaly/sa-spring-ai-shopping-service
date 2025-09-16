package dev.ams.ai.shoppingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ams.ai.shoppingservice.dto.ChatRequest;
import dev.ams.ai.shoppingservice.dto.ChatResponse;
import dev.ams.ai.shoppingservice.dto.RecommendationRequest;
import dev.ams.ai.shoppingservice.dto.RecommendationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/customer-service")
@RequiredArgsConstructor
public class CustomerServiceController {
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        log.info("Received chat request: {}", request);
        String response = chatClient.prompt(request.getMessage()).call().content();
        log.debug("Generated chat response: {}", response);
        return ResponseEntity.ok(new ChatResponse(response));
    }

    @PostMapping("/product-recommendation")
    public ResponseEntity<RecommendationResponse> getRecommendations(@RequestBody RecommendationRequest request) {
        log.info("Generating recommendations for request: {}", request);
        String prompt = String.format(
                "Based on customer age %d, gender %s, and previous purchases, recommend products. " +
                        "Available categories: Electronics, Fashion, Home, Books. " +
                        "Provide up to 3 products recommendations with reasons." +
                        "Respond with a valid JSON array of objects with the following structure: " +
                        "[{\"title\": \"Product 1\", \"category\": \"Category 1\", \"notes\": \"reason 1\"}, ...] " +
                        "Only respond with the raw JSON array without any additional text or formatting." +
                        "Do not include any markdown formatting or backticks in the response. ",
                //"Previous purchases: %s",
                request.getAge(), request.getGender()
        );

        String response = chatClient.prompt(prompt).call().content();
        log.debug("Generated recommendations: {}", response);
        try {
            // Remove markdown code blocks and trim whitespace
            String cleanResponse = response
                    .replaceAll("```(json)?", "")  // Remove markdown code blocks (```json and ```)
                    .replaceAll("^\\s+|\\s+$", "") // Trim leading/trailing whitespace
                    .replaceAll("\\n", "");        // Remove newlines

            String jsonResponse = "{\"recommendedProducts\":" + cleanResponse + "}";
            log.debug("Formatted JSON: {}", jsonResponse);

            RecommendationResponse recommendationResponse = objectMapper.readValue(jsonResponse, RecommendationResponse.class);
            return ResponseEntity.ok(recommendationResponse);
        } catch (JsonProcessingException | NullPointerException e) {
            log.error("Error parsing recommendations: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }

        // return ResponseEntity.ok(new ChatResponse(response));
    }
}
