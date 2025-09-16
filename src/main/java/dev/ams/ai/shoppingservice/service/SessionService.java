package dev.ams.ai.shoppingservice.service;

import dev.ams.ai.shoppingservice.entity.ShoppingSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class SessionService {
    // Ideally this should be on Redis or a database
    private final Map<String, ShoppingSession> activeSessions = new ConcurrentHashMap<>();
    private final EventsPublisherService eventsPublisherService;

    public String createSession(Long customerId) {
        String sessionId = UUID.randomUUID().toString();
        ShoppingSession session = new ShoppingSession(sessionId, customerId);
        activeSessions.put(sessionId, session);

        eventsPublisherService.publishSessionStart(customerId, sessionId);
        return sessionId;
    }

    public void updateSession(String sessionId, Long productId, String action) {
        ShoppingSession session = activeSessions.get(sessionId);
        if (session != null) {
            if ("view".equals(action)) {
                session.getViewedProducts().add(productId);
            }
            // Handle other actions
        }
    }

    public void endSession(String sessionId) {
        ShoppingSession session = activeSessions.remove(sessionId);
        if (session != null) {
            int duration = (int) java.time.Duration.between(
                    session.getStartTime(), java.time.LocalDateTime.now()).getSeconds();

            eventsPublisherService.publishSessionEnd(session.getCustomerId(), sessionId, duration);
        }
    }

    public ShoppingSession getSession(String sessionId) {
        return activeSessions.get(sessionId);
    }
}
