package com.example.jwt_auth_service.mapper;

import com.example.jwt_auth_service.dto.ScorePlanDTO;
import com.example.jwt_auth_service.model.ScorePlan;
import org.springframework.stereotype.Component;

@Component
public class ScorePlanMapper {
    public ScorePlanDTO toDto(ScorePlan scorePlan) {
        if (scorePlan == null) return null;
        ScorePlanDTO dto = new ScorePlanDTO();
        dto.setScoreId(scorePlan.getScoreId());
        dto.setDescription(scorePlan.getDescription());
        dto.setPercentage(scorePlan.getPercentage());
        return dto;
    }
}