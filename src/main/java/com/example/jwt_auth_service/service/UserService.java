package com.example.jwt_auth_service.service;


import com.example.jwt_auth_service.model.User;
import com.example.jwt_auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
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
                    userToUpdate.setPassword((String) value);
                    break;
            }
        });
        return userRepository.save(userToUpdate);
    }
}
