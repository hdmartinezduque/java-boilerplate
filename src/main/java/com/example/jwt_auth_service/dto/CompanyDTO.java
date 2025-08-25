package com.example.jwt_auth_service.dto;

import java.time.LocalDateTime;

public class CompanyDTO {
    private Long id;
    private String companyName;
    private String companyNIT;
    private String email;
    private LocalDateTime createdAt;

    public CompanyDTO() {}

    public CompanyDTO(Long id, String companyName, String companyNIT, String email, LocalDateTime createdAt) {
        this.id = id;
        this.companyName = companyName;
        this.companyNIT = companyNIT;
        this.email = email;
        this.createdAt = createdAt;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getCompanyNIT() { return companyNIT; }
    public void setCompanyNIT(String companyNIT) { this.companyNIT = companyNIT; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
