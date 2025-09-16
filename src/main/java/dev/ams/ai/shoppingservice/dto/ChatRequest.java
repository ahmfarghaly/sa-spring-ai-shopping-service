package dev.ams.ai.shoppingservice.dto;

import lombok.Data;

@Data
public class ChatRequest {
    private String message;
    private Long customerId;
}