package dev.ams.ai.shoppingservice.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Entity
@DiscriminatorValue("SEARCH")
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchEventEntity extends ShoppingEventEntity {
    private String query;
    private Integer resultCount;

    @Override
    public Map<String, Object> getEventData() {
        return Map.of(
                "query", query,
                "resultCount", resultCount
        );
    }
}
