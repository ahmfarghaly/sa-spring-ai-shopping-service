package dev.ams.ai.shoppingservice.service;

import dev.ams.ai.shoppingservice.event.ProductViewEvent;
import dev.ams.ai.shoppingservice.event.PurchaseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShoppingEventListener {
    private final AnalyticsService analyticsService;

    @EventListener
    public void handleProductView(ProductViewEvent event) {
        // Store event for analytics
        log.info("Product viewed: " + event.getProductId() + " by customer: " + event.getCustomerId());
    }

    @EventListener
    public void handlePurchase(PurchaseEvent event) {
        // Process purchase for analytics
        log.info("Purchase made: " + event.getOrderId() + " amount: " + event.getAmount());
    }
}