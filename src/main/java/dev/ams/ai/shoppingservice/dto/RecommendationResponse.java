package dev.ams.ai.shoppingservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class RecommendationResponse {
    List<RecommendedEntity> recommendedProducts;

    @Data
    public static class RecommendedEntity {

        private String title;
        private String category;
        @JsonIgnore private double price;
        private String notes;
    }
}
