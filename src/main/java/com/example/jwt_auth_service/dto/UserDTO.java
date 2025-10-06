package com.example.jwt_auth_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "User response")
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Long idCompany;
    private String address;
    private String companyName;
    private String codeId;
    private String status;     // code: "active"
    private String contract;   // code: "contractor"
    private String telephone;
    private List<ScorePlanDTO> score;
    private String createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Long getIdCompany() { return idCompany; }
    public String getCodeId() { return codeId; }
    public void setCodeId(String codeId) { this.codeId = codeId; }
    public void setIdCompany(Long idCompany) { this.idCompany = idCompany; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getContract() { return contract; }
    public void setContract(String contract) { this.contract = contract; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public List<ScorePlanDTO> getScore() { return score; }
    public void setScore(List<ScorePlanDTO> score) { this.score = score; }
}

