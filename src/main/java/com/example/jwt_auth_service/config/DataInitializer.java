package com.example.jwt_auth_service.config;


import com.example.jwt_auth_service.model.User;
import com.example.jwt_auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Solo poblar si la base de datos está vacía
        if (userRepository.count() == 0) {
            User user1 = new User("Ana García", "ana.garcia@example.com", "pass123");
            User user2 = new User("Carlos Ruiz", "carlos.ruiz@example.com", "pass456");
            User user3 = new User("Laura Méndez", "laura.mendez@example.com", "pass789");

            userRepository.saveAll(Arrays.asList(user1, user2, user3));
            System.out.println("Base de datos poblada con 3 usuarios.");
        }
    }
}