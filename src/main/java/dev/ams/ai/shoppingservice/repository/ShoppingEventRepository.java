package dev.ams.ai.shoppingservice.repository;

import dev.ams.ai.shoppingservice.entity.EventType;
import dev.ams.ai.shoppingservice.entity.ShoppingEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShoppingEventRepository extends JpaRepository<ShoppingEventEntity, Long> {
    List<ShoppingEventEntity> findByCustomerId(Long customerId);
    List<ShoppingEventEntity> findByEventType(EventType eventType);
    List<ShoppingEventEntity> findByCustomerIdAndEventType(Long customerId, EventType eventType);
    List<ShoppingEventEntity> findBySessionId(String sessionId);

    @Query("SELECT e FROM ShoppingEventEntity e WHERE e.timestamp BETWEEN :start AND :end")
    List<ShoppingEventEntity> findEventsBetweenDates(@Param("start") LocalDateTime start,
                                                     @Param("end") LocalDateTime end);

    @Query("SELECT e FROM ShoppingEventEntity e WHERE e.timestamp >= :startDate")
    List<ShoppingEventEntity> findEventsAfterDate(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT e FROM ShoppingEventEntity e WHERE e.customerId = :customerId ORDER BY e.timestamp DESC")
    List<ShoppingEventEntity> findRecentEventsByCustomer(@Param("customerId") Long customerId);

    List<ShoppingEventEntity> findByEntityIdAndEventType(Long entityId, EventType eventType);
}

