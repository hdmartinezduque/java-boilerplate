package com.example.jwt_auth_service.repository;

import com.example.jwt_auth_service.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
    Optional<UserStatus> findByCodeAndEnabledTrue(String code);
}
