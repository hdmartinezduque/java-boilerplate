package com.example.jwt_auth_service.mapper;

import com.example.jwt_auth_service.dto.UserDTO;
import com.example.jwt_auth_service.model.BaseCatalog;
import com.example.jwt_auth_service.model.User;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @SuppressWarnings("unchecked")
    private static <T> T unproxy(T entity) {
        if (entity instanceof HibernateProxy proxy) {
            return (T) proxy.getHibernateLazyInitializer().getImplementation();
        }
        return entity;
    }

    private static String safeCode(Object catalog) {
        if (catalog == null) return null;
        Object target = (catalog instanceof HibernateProxy) ?
                ((HibernateProxy) catalog).getHibernateLazyInitializer().getImplementation()
                : catalog;
        if (target instanceof BaseCatalog bc) {
            return bc.getCode();
        }
        return null;
    }

    public UserDTO toDto(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCodeId(user.getCodeId());

        if (user.getCompany() != null) {
            dto.setIdCompany(user.getCompany().getId());
            dto.setCompanyName(user.getCompany().getName());
        }

        dto.setStatus(safeCode(user.getStatus()));
        dto.setContract(safeCode(user.getContractType()));

        dto.setCreatedAt(user.getCreatedAt() != null ? user.getCreatedAt().toString() : null);
        return dto;
    }

}
