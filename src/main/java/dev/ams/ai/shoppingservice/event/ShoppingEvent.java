package dev.ams.ai.shoppingservice.event;

import dev.ams.ai.shoppingservice.entity.ShoppingEventEntity;
import org.springframework.context.ApplicationEvent;

public abstract class ShoppingEvent extends ApplicationEvent {
    public ShoppingEvent(Object source) {
        super(source);
    }

    public abstract String getEventType();

    public abstract ShoppingEventEntity toEntity();
}