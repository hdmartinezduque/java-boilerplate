package com.example.jwt_auth_service.repository;

import com.example.jwt_auth_service.model.ContractType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractTypeRepository extends JpaRepository<ContractType, Long> {
    Optional<ContractType> findByCodeAndEnabledTrue(String code);
}
