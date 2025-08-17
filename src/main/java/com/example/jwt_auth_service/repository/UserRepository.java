package com.example.jwt_auth_service.repository;


import com.example.jwt_auth_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository nos da métodos como findAll(), findById(), save(), deleteById()...
    // ¡No necesitamos escribir ninguna implementación!
}