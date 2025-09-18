package dev.ams.ai.shoppingservice.controller;

import dev.ams.ai.shoppingservice.dto.ChatRequest;
import dev.ams.ai.shoppingservice.dto.ChatResponse;
import dev.ams.ai.shoppingservice.dto.RecommendationRequest;
import dev.ams.ai.shoppingservice.dto.RecommendationResponse;
import dev.ams.ai.shoppingservice.service.CustomerAssistantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/customer-service")
@RequiredArgsConstructor
public class CustomerServiceController {
    private final CustomerAssistantService customerAssistantService;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        log.info("Received chat request: {}", request);
        return ResponseEntity.ok(customerAssistantService.handleChat(request));
    }

    @PostMapping("/product-recommendation")
    public ResponseEntity<RecommendationResponse> getRecommendations(@RequestBody RecommendationRequest request) {
        log.info("Processing recommendation request: {}", request);
        try {
            return ResponseEntity.ok(customerAssistantService.handleRecommendation(request));
        } catch (Exception e) {
            log.error("Error processing recommendation request: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
