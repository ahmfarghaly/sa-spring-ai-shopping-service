package dev.ams.ai.shoppingservice.controller;

import dev.ams.ai.shoppingservice.dto.OrderRequest;
import dev.ams.ai.shoppingservice.entity.Order;
import dev.ams.ai.shoppingservice.service.EventsPublisherService;
import dev.ams.ai.shoppingservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final EventsPublisherService eventsPublisherService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request,
                                             @RequestHeader(value = "X-Session-ID", required = false) String sessionId) {
        Order order = orderService.createOrder(request);

        eventsPublisherService.publishPurchase(
                request.getCustomerId(),
                order.getId(),
                order.getTotalAmount(),
                request.getItems().stream().mapToInt(OrderRequest.OrderItemRequest::getQuantity).sum(),
                request.getPaymentMethod(),
                sessionId
        );

        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        log.info("Fetching order with id: {}", id);
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        log.info("Fetching all orders");
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderRequest request) {
        log.info("Updating order with id: {}", id);
        return ResponseEntity.ok(orderService.updateOrder(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        log.info("Deleting order with id: {}", id);
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
