package com.example.jwt_auth_service.util;

import java.util.Arrays;
import java.util.List;

public class ConstanatsUrls {
    public static final List<String> PUBLIC_URLS = Arrays.asList(
            "/auth/login",
            "/public/",
            "/auth/register",
            "/public/hello",
            "/api/products",
            "/swagger-ui.html",
            "/swagger-ui/", 
            "/v3/api-docs/", 
            "/swagger-ui/**", 
            "/v3/api-docs/**" 
    );
}
