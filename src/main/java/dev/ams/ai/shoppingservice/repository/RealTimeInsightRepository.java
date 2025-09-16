package dev.ams.ai.shoppingservice.repository;

import dev.ams.ai.shoppingservice.entity.RealTimeInsightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealTimeInsightRepository extends JpaRepository<RealTimeInsightEntity, Long> {
    List<RealTimeInsightEntity> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
    List<RealTimeInsightEntity> findByInsightTypeOrderByCreatedAtDesc(String insightType);
}