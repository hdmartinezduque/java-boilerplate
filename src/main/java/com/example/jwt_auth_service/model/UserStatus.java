package com.example.jwt_auth_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "user_status")
public class UserStatus extends BaseCatalog {

}
