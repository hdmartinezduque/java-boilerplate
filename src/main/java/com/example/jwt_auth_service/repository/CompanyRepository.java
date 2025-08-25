package com.example.jwt_auth_service.repository;

import com.example.jwt_auth_service.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByEmail(String email);
    boolean existsByCompanyNIT(String companyNIT);
}
