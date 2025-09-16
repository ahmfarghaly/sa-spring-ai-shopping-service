package dev.ams.ai.shoppingservice.service;

import dev.ams.ai.shoppingservice.entity.ShoppingEventEntity;
import dev.ams.ai.shoppingservice.event.ProductViewEvent;
import dev.ams.ai.shoppingservice.event.PurchaseEvent;
import dev.ams.ai.shoppingservice.event.SearchEvent;
import dev.ams.ai.shoppingservice.event.ShoppingEvent;
import dev.ams.ai.shoppingservice.repository.ShoppingEventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class EventsPublisherService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ShoppingEventRepository eventRepository;

    @Transactional
    @Async
    public void publishProductView(Long customerId, Long productId, String sessionId,
                                   Integer viewDuration, String viewSource) {
        ProductViewEvent event = new ProductViewEvent(this, customerId, productId,
                sessionId, viewDuration, viewSource);
        applicationEventPublisher.publishEvent(event);
        saveEvent(event);
    }

    @Transactional
    @Async
    public void publishPurchase(Long customerId, Long orderId, BigDecimal amount,
                                Integer itemCount, String paymentMethod, String sessionId) {
        PurchaseEvent event = new PurchaseEvent(this, customerId, orderId, amount,
                itemCount, paymentMethod, sessionId);
        applicationEventPublisher.publishEvent(event);
        saveEvent(event);
    }

    @Transactional
    @Async
    public void publishSearch(Long customerId, String searchQuery, Integer resultCount, String sessionId) {
        SearchEvent event = new SearchEvent(this, searchQuery, resultCount, customerId, sessionId);
        applicationEventPublisher.publishEvent(event);
        saveEvent(event);
    }

    @Transactional
    public void publishSessionStart(Long customerId, String sessionId) {
        //TODO implement session start events
    }

    @Transactional
    public void publishSessionEnd(Long customerId, String sessionId, Integer duration) {
        //TODO implement session end events
    }

    private void saveEvent(ShoppingEvent event) {
        ShoppingEventEntity entity = event.toEntity();
        eventRepository.save(entity);
    }
}

