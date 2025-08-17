package com.example.jwt_auth_service.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "users") // Le decimos a Hibernate que esta entidad mapea a la tabla 'users'
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental en MySQL
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    
    @CreationTimestamp // Hibernate asignará la fecha y hora actual al crear el registro
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // --- Constructores, Getters y Setters ---

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    // Getters y Setters... (puedes generarlos con tu IDE)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}