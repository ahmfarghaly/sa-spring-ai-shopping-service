package dev.ams.ai.shoppingservice.service;

import dev.ams.ai.shoppingservice.AIUtility;
import dev.ams.ai.shoppingservice.entity.Customer;
import dev.ams.ai.shoppingservice.entity.Product;
import dev.ams.ai.shoppingservice.entity.RealTimeInsightEntity;
import dev.ams.ai.shoppingservice.event.ProductViewEvent;
import dev.ams.ai.shoppingservice.event.PurchaseEvent;
import dev.ams.ai.shoppingservice.event.SearchEvent;
import dev.ams.ai.shoppingservice.repository.CustomerRepository;
import dev.ams.ai.shoppingservice.repository.ProductRepository;
import dev.ams.ai.shoppingservice.repository.RealTimeInsightRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class RealTimeAnalyticsProcessor {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final RealTimeInsightRepository insightRepository;
    private final AIUtility aiUtility;

    @Async
    @Transactional
    @EventListener
    public void processProductView(ProductViewEvent event) {
        log.info("Processing product view event for product: {}", event.getProductId());
        String prompt = buildProductViewPrompt(event);
        String analysis = aiUtility.callAiWithFallback(prompt);
        storeRealTimeInsight(event.getCustomerId(), "view_analysis", analysis, event.getProductId());
        log.info("Product view analysis generated for product: {} with analysis: {}", event.getProductId(), analysis);
    }

    @Async
    @Transactional
    @EventListener
    public void processPurchase(PurchaseEvent event) {
        log.info("Processing purchase event for order: {}", event.getOrderId());
        String prompt = buildPurchasePrompt(event);
        String analysis = aiUtility.callAiWithFallback(prompt);
        storeRealTimeInsight(event.getCustomerId(), "purchase_analysis", analysis, event.getOrderId());
        log.info("Purchase analysis generated for order: {} with analysis: {}", event.getOrderId(), analysis);
    }

    @Async
    @Transactional
    @EventListener
    public void processSearch(SearchEvent event) {
        log.info("Processing search event for query: {}", event.getQuery());
        String prompt = buildSearchPrompt(event);
        String analysis = aiUtility.callAiWithFallback(prompt);
        storeRealTimeInsight(event.getCustomerId(), "search_analysis", analysis, null);
        log.info("Search analysis generated for query: {} with analysis: {}", event.getQuery(), analysis);
    }



    private String buildProductViewPrompt(ProductViewEvent event) {
        Customer customer = customerRepository.findById(event.getCustomerId()).orElse(null);
        Product product = productRepository.findById(event.getProductId()).orElse(null);

        return String.format(
                "Customer %s (age %d, gender %s) viewed product: %s (category: %s, price: %s) " +
                        "for %d seconds. Based on this viewing pattern and customer demographics, " +
                        "suggest 3 complementary products and explain why they would be good recommendations. " +
                        "Also analyze if this indicates high purchase intent.",
                customer != null ? customer.getName() : "Unknown",
                customer != null ? customer.getAge() : 0,
                customer != null ? customer.getGender().toString() : "Unknown",
                product != null ? product.getTitle() : "Unknown",
                product != null ? product.getCategory() : "Unknown",
                product != null ? product.getPrice().toString() : "0",
                event.getViewDuration()
        );
    }

    private String buildPurchasePrompt(PurchaseEvent event) {
        Customer customer = customerRepository.findById(event.getCustomerId()).orElse(null);

        return String.format(
                "Customer %s (age %d, gender %s) made a purchase of %s with %d items. " +
                        "Based on this purchase behavior, suggest 3 complementary products for future recommendations " +
                        "and analyze the customer's potential value segment (high-value, medium-value, low-value).",
                customer != null ? customer.getName() : "Unknown",
                customer != null ? customer.getAge() : 0,
                customer != null ? customer.getGender().toString() : "Unknown",
                event.getAmount().toString(),
                event.getItemCount()
        );
    }

    private String buildSearchPrompt(SearchEvent event) {
        Customer customer = customerRepository.findById(event.getCustomerId()).orElse(null);

        return String.format(
                "Customer %s (age %d, gender %s) searched for '%s' and found %d results. " +
                        "Analyze the search intent and suggest potential product recommendations or " +
                        "content improvements based on this search behavior.",
                customer != null ? customer.getName() : "Unknown",
                customer != null ? customer.getAge() : 0,
                customer != null ? customer.getGender().toString() : "Unknown",
                event.getQuery(),
                event.getResultCount()
        );
    }

    @Transactional
    public void storeRealTimeInsight(Long customerId, String insightType, String insightData, Long entityId) {
        RealTimeInsightEntity insight = new RealTimeInsightEntity();
        insight.setCustomerId(customerId);
        insight.setInsightType(insightType);
        insight.setInsightData(insightData);
        insight.setEntityId(entityId);
        insight.setCreatedAt(LocalDateTime.now());

        insightRepository.save(insight);
    }

}
