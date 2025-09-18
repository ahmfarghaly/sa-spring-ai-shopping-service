package dev.ams.ai.shoppingservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIUtility {

    private final ChatClient chatClient;

    public String callAiWithFallback(String prompt) {
        try {
            return chatClient.prompt(prompt).call().content();
        } catch (Exception e) {
            log.error("AI call failed: {}", e.getMessage());
            return "Service unavailable at the moment.";
        }
    }
}
