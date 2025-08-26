package com.example.jwt_auth_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedocController {
    @GetMapping("/redoc")
    public String redoc() {
        return "redoc";
    }
}
