package dev.ams.ai.shoppingservice.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Entity
@DiscriminatorValue("PRODUCT_VIEW")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductViewEventEntity extends ShoppingEventEntity {
    private Integer viewDuration; // seconds
    private String viewSource; // search, category, recommendation

    @Override
    public Map<String, Object> getEventData() {
        return Map.of(
                "viewDuration", viewDuration,
                "viewSource", viewSource
        );
    }
}

