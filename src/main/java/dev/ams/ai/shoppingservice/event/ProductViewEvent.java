package dev.ams.ai.shoppingservice.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ams.ai.shoppingservice.entity.EventType;
import dev.ams.ai.shoppingservice.entity.ProductViewEventEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@Slf4j
@Getter
@Setter
public class ProductViewEvent extends ShoppingEvent {
    private Long customerId;
    private Long productId;
    private String sessionId;
    private Integer viewDuration;
    private String viewSource;

    public ProductViewEvent(Object source, Long customerId, Long productId,
                            String sessionId, Integer viewDuration, String viewSource) {
        super(source);
        this.customerId = customerId;
        this.productId = productId;
        this.sessionId = sessionId;
        this.viewDuration = viewDuration;
        this.viewSource = viewSource;
    }

    public ProductViewEventEntity toEntity() {
        ProductViewEventEntity entity = new ProductViewEventEntity();
        entity.setCustomerId(customerId);
        entity.setEntityId(productId);
        entity.setEventType(EventType.PRODUCT_VIEW);
        entity.setSessionId(sessionId);
        entity.setViewDuration(viewDuration);
        entity.setViewSource(viewSource);
        entity.setTimestamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(getTimestamp()), ZoneId.systemDefault()));

        // Convert metadata to JSON
        ObjectMapper mapper = new ObjectMapper();
        try {
            entity.setMetadata(mapper.writeValueAsString(Map.of(
                    "viewDuration", viewDuration,
                    "viewSource", viewSource
            )));
        } catch (JsonProcessingException e) {
            log.error("Failed to convert metadata to JSON", e);
        }

        return entity;
    }

    public String getEventType() {
        return "PRODUCT_VIEW";
    }
}