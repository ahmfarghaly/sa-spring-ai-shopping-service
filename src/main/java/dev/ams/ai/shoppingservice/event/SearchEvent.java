package dev.ams.ai.shoppingservice.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ams.ai.shoppingservice.entity.EventType;
import dev.ams.ai.shoppingservice.entity.SearchEventEntity;
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
public class SearchEvent extends ShoppingEvent {

    private String query;
    private Integer resultCount;
    private Long customerId;
    private String sessionId;

    public SearchEvent(Object source, String query, Integer resultCount, Long customerId, String sessionId) {
        super(source);
        this.customerId=customerId;
        this.query=query;
        this.resultCount=resultCount;
        this.sessionId=sessionId;
    }

    public SearchEventEntity toEntity() {
        SearchEventEntity entity = new SearchEventEntity();

        entity.setCustomerId(customerId);
        entity.setQuery(query);
        entity.setResultCount(resultCount);
        entity.setSessionId(sessionId);
        entity.setTimestamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(getTimestamp()), ZoneId.systemDefault()));
        entity.setEventType(EventType.SEARCH);

        ObjectMapper mapper = new ObjectMapper();
        try {
            entity.setMetadata(mapper.writeValueAsString(Map.of(
                    "query", query,
                    "resultCount", resultCount
            )));
        } catch (JsonProcessingException e) {
            log.error("Failed to convert metadata to JSON", e);
        }
        return entity;
    }

    public String getEventType() {
        return "SEARCH";
    }
}
