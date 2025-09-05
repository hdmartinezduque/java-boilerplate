package com.example.jwt_auth_service.repository;

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
}
