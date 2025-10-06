// src/main/java/com/example/jwt_auth_service/model/ScorePlan.java
package com.example.jwt_auth_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "score_plan")
public class ScorePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double percentage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code_id", referencedColumnName = "code_id", nullable = false)
    private User user;

    // Getters and setters
    public Long getScoreId() { return scoreId; }
    public void setScoreId(Long scoreId) { this.scoreId = scoreId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) { this.percentage = percentage; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
