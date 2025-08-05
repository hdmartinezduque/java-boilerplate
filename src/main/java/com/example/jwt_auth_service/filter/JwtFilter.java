package com.example.jwt_auth_service.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.jwt_auth_service.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Component
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                String username = jwtUtil.validateTokenAndRetrieveSubject(token);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Si el token es válido, continúa con el siguiente filtro en la cadena
                chain.doFilter(request, response);
                return;

            } catch (TokenExpiredException e) {
                // Captura la excepción de token expirado y genera una respuesta de error
                handleExpiredTokenError(httpResponse, httpRequest);
                return; // Importante: detén la cadena de filtros
            } catch (Exception e) {
                // Captura cualquier otra excepción relacionada con el token
                handleInvalidTokenError(httpResponse, httpRequest);
                return; // Importante: detén la cadena de filtros
            }
        }

        // Si no hay token en el header o no es Bearer, continúa la cadena de filtros
        chain.doFilter(request, response);
    }

    private void handleExpiredTokenError(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> errorDetails = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.UNAUTHORIZED.value(),
                "error", "Token Expired",
                "message", "El token ha expirado.",
                "path", request.getRequestURI()
        );

        new ObjectMapper().writeValue(response.getOutputStream(), errorDetails);
    }

    private void handleInvalidTokenError(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> errorDetails = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.UNAUTHORIZED.value(),
                "error", "Unauthorized",
                "message", "Token inválido.",
                "path", request.getRequestURI()
        );

        new ObjectMapper().writeValue(response.getOutputStream(), errorDetails);
    }
}