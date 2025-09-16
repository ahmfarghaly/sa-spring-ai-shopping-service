package dev.ams.ai.shoppingservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatResponse {
    private String response;
    private LocalDateTime timestamp;

    public ChatResponse(String response) {
        this.response = response;
        this.timestamp = LocalDateTime.now();
    }
}