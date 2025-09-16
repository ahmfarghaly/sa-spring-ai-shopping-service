package dev.ams.ai.shoppingservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long customerId;
    private List<OrderItemRequest> items;
    private String status;
    private String paymentMethod;

    @Data
    public static class OrderItemRequest {
        private Long productId;
        private Integer quantity;
    }
}