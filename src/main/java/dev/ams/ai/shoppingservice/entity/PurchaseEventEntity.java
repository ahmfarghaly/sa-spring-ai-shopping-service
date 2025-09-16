package dev.ams.ai.shoppingservice.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@DiscriminatorValue("PURCHASE")
@Data
@EqualsAndHashCode(callSuper = true)
public class PurchaseEventEntity extends ShoppingEventEntity {
    private BigDecimal amount;
    private Integer itemCount;
    private String paymentMethod;

    @Override
    public Map<String, Object> getEventData() {
        return Map.of(
                "amount", amount,
                "itemCount", itemCount,
                "paymentMethod", paymentMethod
        );
    }
}