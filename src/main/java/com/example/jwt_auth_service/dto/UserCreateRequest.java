package com.example.jwt_auth_service.dto;

import jakarta.validation.constraints.*;

public class UserCreateRequest {
    @NotBlank private String name;
    @Email @NotBlank private String email;
    @NotBlank private String password;
    @NotNull  private Long companyId;
    @NotBlank private String status;   // "active" / "inactive"
    @NotBlank private String contract; // "contractor" / "worker"

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getContract() { return contract; }
    public void setContract(String contract) { this.contract = contract; }
}
