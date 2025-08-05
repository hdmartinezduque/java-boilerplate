package com.example.jwt_auth_service.service;

import com.example.jwt_auth_service.config.JwtProperties;
import com.example.jwt_auth_service.security.JwtUtil;
import org.springframework.stereotype.Service;

import com.example.jwt_auth_service.exception.AuthenticationException;


@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;

    public AuthService(JwtUtil jwtUtil, JwtProperties jwtProperties) {
        this.jwtUtil = jwtUtil;
        this.jwtProperties = jwtProperties;
    }

    public String authenticate(String username, String password) {
        if (jwtProperties.getUserName().equals(username) && jwtProperties.getPassword().equals(password)) {
            return jwtUtil.generateToken(username);
        }
        throw new AuthenticationException("Invalid Credencial");
        //throw new RuntimeException("Credenciales inválidas");
    }
}
