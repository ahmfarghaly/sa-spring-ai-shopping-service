package dev.ams.ai.shoppingservice.service;

import dev.ams.ai.shoppingservice.entity.*;
import dev.ams.ai.shoppingservice.repository.CustomerRepository;
import dev.ams.ai.shoppingservice.repository.OrderRepository;
import dev.ams.ai.shoppingservice.repository.ProductRepository;
import dev.ams.ai.shoppingservice.repository.ShoppingEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final ChatClient chatClient;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final ShoppingEventRepository eventRepository;

    public String analyzeCustomerBehavior(Long customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        Customer customer = customerRepository.findById(customerId).orElseThrow();

        String prompt = String.format(
                "Analyze shopping behavior for customer: %s, age %d, gender %s. " +
                        "Order history: %d orders. Provide insights and recommendations.",
                customer.getName(), customer.getAge(), customer.getGender(), orders.size()
        );

        return chatClient.prompt(prompt).call().content();
    }

    public String analyzeProductTrends() {

        String prompt = "Analyze product trends based on available products and order history. " +
                "Identify popular categories and suggest inventory adjustments.";

        return chatClient.prompt(prompt).call().content();
    }

    public String analyzeCustomerSegments() {
        List<Customer> customers = customerRepository.findAll();
        List<Order> orders = orderRepository.findAll();

        String prompt = "Analyze customer segments based on the following data: " +
                "Total customers: " + customers.size() + ". " +
                "Total orders: " + orders.size() + ". " +
                "Provide insights about different customer segments and their shopping patterns.";

        return chatClient.prompt(prompt).call().content();
    }
    public String analyzeCustomerJourney(Long customerId) {
        List<ShoppingEventEntity> events = eventRepository.findByCustomerId(customerId);
        Customer customer = customerRepository.findById(customerId).orElseThrow();

        String eventTimeline = buildEventTimeline(events);

        String prompt = String.format(
                """
                        Analyze the complete customer journey for %s (age %d, gender %s):
                        
                        Event Timeline:
                        %s
                        
                        Provide insights on:
                        1. Shopping behavior patterns
                        2. Purchase intent signals
                        3. Potential friction points
                        4. Personalization opportunities
                        5. Recommended next actions""",
                customer.getName(), customer.getAge(), customer.getGender(), eventTimeline
        );

        return chatClient.prompt(prompt).call().content();
    }

    public String analyzeProductPerformance(Long productId) {
        List<ShoppingEventEntity> viewEvents = eventRepository.findByEntityIdAndEventType(
                productId, EventType.PRODUCT_VIEW);
        List<ShoppingEventEntity> purchaseEvents = eventRepository.findByEntityIdAndEventType(
                productId, EventType.PURCHASE);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        String prompt = String.format(
                """
                        Analyze performance for product: %s
                        Total Views: %d
                        Total Purchases: %d
                        Conversion Rate: %.2f%%
                        
                        Provide insights on:
                        1. View-to-purchase conversion effectiveness
                        2. Potential pricing or positioning issues
                        3. Cross-selling opportunities
                        4. Inventory and stock recommendations""",
                product.getTitle(),
                viewEvents.size(),
                purchaseEvents.size(),
                !viewEvents.isEmpty() ? (purchaseEvents.size() * 100.0 / viewEvents.size()) : 0
        );

        return chatClient.prompt(prompt).call().content();
    }

    public String predictCustomerLTV(Long customerId) {
        List<ShoppingEventEntity> events = eventRepository.findByCustomerId(customerId);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        String purchaseHistory = buildPurchaseHistory(events);

        String prompt = String.format(
                """
                        Predict Customer Lifetime Value for %s based on:
                        Age: %d, Gender: %s
                        Purchase History:
                        %s
                        
                        Consider:
                        1. Current spending patterns
                        2. Demographic factors
                        3. Engagement level
                        4. Provide predicted LTV range and confidence level""",
                customer.getName(), customer.getAge(), customer.getGender(), purchaseHistory
        );

        return chatClient.prompt(prompt).call().content();
    }

    public String getTrendingProducts(int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        List<ShoppingEventEntity> recentEvents = eventRepository.findEventsAfterDate(startDate);

        // Group by product and count views
        Map<Long, Long> productViewCounts = recentEvents.stream()
                .filter(event -> event.getEventType() == EventType.PRODUCT_VIEW)
                .collect(Collectors.groupingBy(ShoppingEventEntity::getEntityId, Collectors.counting()));

        // Get top 5 products
        List<Long> trendingProductIds = productViewCounts.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Get product details
        List<Product> trendingProducts = productRepository.findAllById(trendingProductIds);

        String prompt = String.format(
                """
                        Analyze trending products over the last %d days. Top products by views:
                        %s
                        
                        Provide insights on:
                        1. Common characteristics of trending products
                        2. Seasonal or contextual factors influencing trends
                        3. Recommendations for inventory and marketing""",
                days,
                trendingProducts.stream()
                        .map(p -> String.format("- %s: %s (Category: %s, Price: %s)",
                                p.getTitle(), p.getDescription(), p.getCategory(), p.getPrice()))
                        .collect(Collectors.joining("\n"))
        );

        return chatClient.prompt(prompt).call().content();
    }

    private String buildEventTimeline(List<ShoppingEventEntity> events) {
        return events.stream()
                .sorted(Comparator.comparing(ShoppingEventEntity::getTimestamp))
                .map(event -> String.format("%s: %s (%s)",
                        event.getTimestamp(), event.getEventType(), event.getEntityId()))
                .collect(Collectors.joining("\n"));
    }

    private String buildPurchaseHistory(List<ShoppingEventEntity> events) {
        List<ShoppingEventEntity> purchaseEvents = events.stream()
                .filter(event -> event.getEventType() == EventType.PURCHASE)
                .toList();

        return purchaseEvents.stream()
                .map(event -> {
                    PurchaseEventEntity purchaseEvent = (PurchaseEventEntity) event;
                    return String.format("Purchase on %s: %s for %s",
                            event.getTimestamp(), purchaseEvent.getItemCount(), purchaseEvent.getAmount());
                })
                .collect(Collectors.joining("\n"));
    }
}