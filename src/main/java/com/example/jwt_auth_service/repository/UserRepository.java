package com.example.jwt_auth_service.repository;


import com.example.jwt_auth_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository nos da métodos como findAll(), findById(), save(), deleteById()...
    Optional<User> findByEmail(String email);
}