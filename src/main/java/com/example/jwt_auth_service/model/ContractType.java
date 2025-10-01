package com.example.jwt_auth_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "contract_type")
public class ContractType extends BaseCatalog {
}
