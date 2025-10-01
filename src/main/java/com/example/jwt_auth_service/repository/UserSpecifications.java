package com.example.jwt_auth_service.repository;

import com.example.jwt_auth_service.model.Company;
import com.example.jwt_auth_service.model.User;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<User> withStatusCode(String statusCode) {
        return (root, query, cb) -> {
            if (statusCode == null || statusCode.isBlank()) return null;
            var join = root.join("status", JoinType.INNER);
            return cb.equal(cb.lower(join.get("code")), statusCode.toLowerCase());
        };
    }

    public static Specification<User> withContractCode(String contractCode) {
        return (root, query, cb) -> {
            if (contractCode == null || contractCode.isBlank()) return null;
            var join = root.join("contractType", JoinType.INNER);
            return cb.equal(cb.lower(join.get("code")), contractCode.toLowerCase());
        };
    }

    public static Specification<User> withNameLike(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<User> withEmailLike(String email) {
        return (root, query, cb) -> {
            if (email == null || email.isBlank()) return null;
            return cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
        };
    }

    public static Specification<User> withCodeId(String codeId) {
        return (root, query, cb) -> {
            if (codeId == null || codeId.isBlank()) return null;
            return cb.like(cb.lower(root.get("codeId")), "%" + codeId.toLowerCase() + "%");
        };
    }

    public static Specification<User> withCompanyNameLike(String companyName) {
        return (root, query, cb) -> {
            System.out.println(companyName);
            if (companyName == null || companyName.isBlank()) return null;
            var join = root.join("company", JoinType.INNER);
            return cb.like(cb.lower(join.get("companyName")), "%" + companyName.toLowerCase() + "%");
        };
    }
}
