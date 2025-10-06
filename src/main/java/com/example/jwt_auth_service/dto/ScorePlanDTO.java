// src/main/java/com/example/jwt_auth_service/dto/ScorePlanDTO.java
package com.example.jwt_auth_service.dto;

public class ScorePlanDTO {
    private Long scoreId;
    private String description;
    private Double percentage;

    // Getters and setters
    public Long getScoreId() { return scoreId; }
    public void setScoreId(Long scoreId) { this.scoreId = scoreId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) { this.percentage = percentage; }
}
