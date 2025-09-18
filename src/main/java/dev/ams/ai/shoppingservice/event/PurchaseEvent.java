package dev.ams.ai.shoppingservice.event;

import dev.ams.ai.shoppingservice.entity.EventType;
import dev.ams.ai.shoppingservice.entity.PurchaseEventEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@Slf4j
@Getter
@Setter
public class PurchaseEvent extends ShoppingEvent {
    private Long customerId;
    private BigDecimal amount;
    private Integer itemCount;
    private String paymentMethod;
    private Long orderId;
    private String sessionId;

    public PurchaseEvent(Object source, Long customerId, Long orderId,
                         BigDecimal amount, Integer itemCount, String paymentMethod, String sessionId) {
        super(source);
        this.customerId = customerId;
        this.orderId = orderId;
        this.amount = amount;
        this.itemCount = itemCount;
        this.paymentMethod = paymentMethod;
        this.sessionId = sessionId;
    }

    public PurchaseEventEntity toEntity() {
        PurchaseEventEntity entity = new PurchaseEventEntity();
        entity.setCustomerId(customerId);
        entity.setEntityId(orderId);
        entity.setAmount(amount);
        entity.setItemCount(itemCount);
        entity.setPaymentMethod(paymentMethod);
        entity.setSessionId(sessionId);
        entity.setTimestamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(getTimestamp()), ZoneId.systemDefault()));
        entity.setEventType(EventType.PURCHASE);

        entity.setMetadata(Map.of(
                "amount", amount,
                "itemCount", itemCount,
                "paymentMethod", paymentMethod
        ));

        return entity;
    }

    @Override
    public String getEventType() {
        return "PURCHASE";
    }
}