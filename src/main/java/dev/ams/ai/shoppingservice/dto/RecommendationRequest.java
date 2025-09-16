package dev.ams.ai.shoppingservice.dto;

import dev.ams.ai.shoppingservice.entity.Gender;
import lombok.Data;

@Data
public class RecommendationRequest {
    private Integer age;
    private Gender gender;
}

