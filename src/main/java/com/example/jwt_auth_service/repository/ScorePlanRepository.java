// src/main/java/com/example/jwt_auth_service/repository/ScorePlanRepository.java
package com.example.jwt_auth_service.repository;

import com.example.jwt_auth_service.model.ScorePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScorePlanRepository extends JpaRepository<ScorePlan, Long> {
    Optional<ScorePlan> findFirstByUser_CodeId(String codeId);
    List<ScorePlan> findAllByUser_CodeId(String codeId);
}
