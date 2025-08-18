package com.example.jwt_auth_service.service;

import com.example.jwt_auth_service.config.JwtProperties;
import com.example.jwt_auth_service.model.User;
import com.example.jwt_auth_service.repository.UserRepository;
import com.example.jwt_auth_service.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwt_auth_service.exception.AuthenticationException;


@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtUtil jwtUtil,
                       JwtProperties jwtProperties,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String authenticate (String email, String rawPassword) throws AuthenticationException{
        User emailUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new AuthenticationException("Invalid Credential"));
        if (passwordEncoder.matches(rawPassword, emailUser.getPassword())){
            return jwtUtil.generateToken(emailUser.getUsername());
        }
        throw new AuthenticationException("Invalid Credential");
    }

}
