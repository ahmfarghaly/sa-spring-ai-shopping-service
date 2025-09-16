package dev.ams.ai.shoppingservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "realtime_insights")
@Data
public class RealTimeInsightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String insightType;

    @Column(columnDefinition = "TEXT")
    private String insightData;

    private Long entityId; // Product ID, Order ID, etc.

    @CreationTimestamp
    private LocalDateTime createdAt;
}
