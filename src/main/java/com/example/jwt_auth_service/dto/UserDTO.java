package com.example.jwt_auth_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User default constructor")
public class UserDTO {

    @Schema(description = "User ID", example = "1")
    private Long id;
    @Schema(description = "User Name", example = "John Doe")
    private String name;
    @Schema(description = "User Email", example = "user@example.com")
    private String email;
    @Schema(description = "User Password", example = "securePassword123")
    private String password;      // Para requests
    @Schema(description = "Company ID associated with the user", example = "2")
    private Long idCompany;       // Para requests
    private String companyName;   // Para responses



    public UserDTO() {
    }

    public UserDTO(Long id, String name, String email, String companyName) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.companyName = companyName;
    }

    public UserDTO(com.example.jwt_auth_service.model.User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.companyName = user.getCompany() != null ? user.getCompany().getName() : null;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Long idCompany) {
        this.idCompany = idCompany;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
