package com.example.jwt_auth_service.util;

import java.util.Arrays;
import java.util.List;

public class ConstanatsUrls {
    public static final List<String> PUBLIC_URLS = Arrays.asList(
            "/auth/login",
            "/auth/register",
            "/public/hello",
            "/api/products",
            "/swagger-ui.html",
            "/swagger-ui/", // Asegúrate de incluir la barra final si es necesario
            "/v3/api-docs/", // Asegúrate de incluir la barra final si es necesario
            "/swagger-ui/**", // Para cubrir subrutas de Swagger UI
            "/v3/api-docs/**" // Para cubrir subrutas de OpenAPI docs
    );
}
