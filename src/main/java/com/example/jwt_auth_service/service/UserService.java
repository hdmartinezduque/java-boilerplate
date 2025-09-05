package com.example.jwt_auth_service.service;

import com.example.jwt_auth_service.dto.*;
import com.example.jwt_auth_service.mapper.UserMapper;
import com.example.jwt_auth_service.model.Company;
import com.example.jwt_auth_service.model.ContractType;
import com.example.jwt_auth_service.model.User;
import com.example.jwt_auth_service.model.UserStatus;
import com.example.jwt_auth_service.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserStatusRepository userStatusRepository;
    private final ContractTypeRepository contractTypeRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       CompanyRepository companyRepository,
                       PasswordEncoder passwordEncoder,
                       UserStatusRepository userStatusRepository,
                       ContractTypeRepository contractTypeRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
        this.userStatusRepository = userStatusRepository;
        this.contractTypeRepository = contractTypeRepository;
        this.userMapper = userMapper;
    }

    public PageResponse<UserDTO> getAllUsers(int page, int size, String sortBy, String sortDir,
                                             String statusCode, String contractCode) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // Compatible con Spring Data JPA 3.3+: allOf ignora nulls
        Specification<User> spec = Specification.allOf(
                UserSpecifications.withStatusCode(statusCode),
                UserSpecifications.withContractCode(contractCode)
        );

        Page<User> pageResult = userRepository.findAll(spec, pageable);
        List<UserDTO> content = pageResult.getContent().stream().map(userMapper::toDto).toList();

        return new PageResponse<>(
                content,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.isLast()
        );
    }

    @Transactional
    public UserDTO getByEmail(String email) {
        User user = userRepository.findByEmail(email.trim())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("User not found with email: " + email));
        return userMapper.toDto(user);
    }

    @Transactional
    public UserDTO createUser(UserCreateRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use: " + req.getEmail());
        }

        Company company = companyRepository.findById(req.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found with ID: " + req.getCompanyId()));

        UserStatus status = userStatusRepository.findByCodeAndEnabledTrue(req.getStatus())
                .orElseThrow(() -> new IllegalArgumentException("Invalid status code: " + req.getStatus()));

        ContractType contractType = contractTypeRepository.findByCodeAndEnabledTrue(req.getContract())
                .orElseThrow(() -> new IllegalArgumentException("Invalid contract code: " + req.getContract()));

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setCompany(company);
        user.setStatus(status);
        user.setContractType(contractType);

        return userMapper.toDto(userRepository.save(user));
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public UserDTO updateUserPartial(Long id, Map<String, Object> updates) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User by Id could not find it: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> u.setName((String) value);
                case "email" -> u.setEmail((String) value);
                case "password" -> u.setPassword(passwordEncoder.encode((String) value));
                case "companyId" -> {
                    Long cid = Long.valueOf(value.toString());
                    Company ref = entityManager.getReference(Company.class, cid);
                    u.setCompany(ref);
                }
                case "status" -> {
                    String code = (String) value;
                    UserStatus st = userStatusRepository.findByCodeAndEnabledTrue(code)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid status code: " + code));
                    u.setStatus(st);
                }
                case "contract" -> {
                    String code = (String) value;
                    ContractType ct = contractTypeRepository.findByCodeAndEnabledTrue(code)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid contract code: " + code));
                    u.setContractType(ct);
                }
                default -> { /* ignorar */ }
            }
        });

        return userMapper.toDto(userRepository.save(u));
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
