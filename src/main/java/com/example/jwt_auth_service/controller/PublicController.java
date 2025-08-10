package com.example.jwt_auth_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {



    @GetMapping("/test")
    public Map<String, String> publicEndpoint() {
        return Map.of("message", "Este endpoint es público");
    }

}
