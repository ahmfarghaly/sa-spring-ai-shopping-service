package dev.ams.ai.shoppingservice.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ShoppingSession {
    private String sessionId;
    private Long customerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Long> viewedProducts = new ArrayList<>();
    private List<Long> cartActions = new ArrayList<>();
    private List<String> searchQueries = new ArrayList<>();

    public ShoppingSession(String sessionId, Long customerId) {
        this.sessionId = sessionId;
        this.customerId = customerId;
        this.startTime = LocalDateTime.now();
    }
}
