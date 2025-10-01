package com.example.jwt_auth_service.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseCatalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable=false, unique=true, length=50)
    protected String code; // p.ej. "active", "inactive", "contractor", "worker"

    @Column(nullable=false, length=120)
    protected String name; // etiqueta legible

    @Column(nullable=false)
    protected boolean enabled = true;

    @CreationTimestamp
    @Column(name="created_at", updatable=false)
    protected LocalDateTime createdAt;

    // getters/setters...

    public Long getId() { return id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
