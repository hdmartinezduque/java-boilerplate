package com.example.jwt_auth_service.service;


import com.example.jwt_auth_service.dto.PageResponse;
import com.example.jwt_auth_service.model.User;
import com.example.jwt_auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

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

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

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
                    userToUpdate.setPassword((String) value);
                    break;
            }
        });
        return userRepository.save(userToUpdate);
    }
}
