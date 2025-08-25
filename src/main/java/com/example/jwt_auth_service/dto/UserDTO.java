package com.example.jwt_auth_service.dto;

public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String password;      // Para requests
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
