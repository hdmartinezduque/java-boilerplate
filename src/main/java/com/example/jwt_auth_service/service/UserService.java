package com.example.jwt_auth_service.service;


import com.example.jwt_auth_service.dto.PageResponse;
import com.example.jwt_auth_service.dto.UserCreateRequest;
import com.example.jwt_auth_service.model.Company;
import com.example.jwt_auth_service.model.User;
import com.example.jwt_auth_service.repository.UserRepository;
import jakarta.transaction.Transactional;
//import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.example.jwt_auth_service.repository.CompanyRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public PageResponse<User> getAllUsers(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> pageResult = userRepository.findAll(pageable);

        List<User> users = pageResult.getContent();

        return new PageResponse<>(
                users,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.isLast()
        );
      }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public User createUser(User user, Long companyId) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use: " + user.getEmail());
        }
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with ID: " + companyId));
        user.setCompany(company);

        return userRepository.save(user);
    }

    @Transactional
    public User updateUserPartial(Long id, Map<String, Object> updates) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User by Id could not find it: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    userToUpdate.setName((String) value);
                    break;
                case "email":
                    userToUpdate.setEmail((String) value);
                    break;
                case "password":
                    String hashedPassword = passwordEncoder.encode((String) value);
                    userToUpdate.setPassword(hashedPassword);
                    break;
                case "companyId":
                    Long cid = Long.valueOf(value.toString());
                    Company ref = entityManager.getReference(Company.class, cid);
                    userToUpdate.setCompany(ref);
                    break;
                default:
                    // Ignorar campos no permitidos
                    break;
            }
        });

        return userRepository.save(userToUpdate);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
