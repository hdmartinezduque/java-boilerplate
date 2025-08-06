package com.example.jwt_auth_service.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.jwt_auth_service.exception.AccessDeniedException;
import com.example.jwt_auth_service.exception.TkExpiredException;
import com.example.jwt_auth_service.exception.AuthenticationException; // Importa tu clase AuthenticationException
import com.example.jwt_auth_service.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.jwt_auth_service.util.ConstanatsUrls.PUBLIC_URLS;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtUtil = jwtUtil;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        // Lista de URLs que son públicas y no requieren token de autenticación
        // 1. Verificar si la URL es pública
        // Si la URL de la solicitud comienza con alguna de las URLs públicas,
        // permitimos que la solicitud pase sin validación de token.
        boolean isPublicUrl = PUBLIC_URLS.stream().anyMatch(requestURI::startsWith);

        if (isPublicUrl) {
            filterChain.doFilter(request, response);
            return; // Detenemos la ejecución del filtro aquí
        }

        // 2. Obtener el encabezado de autorización
        String authHeader = request.getHeader("Authorization");

        // 3. Validar la presencia y formato del token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Extraer el token

            try {
                // 4. Validar el token y establecer la autenticación en el contexto de seguridad
                String username = jwtUtil.validateTokenAndRetrieveSubject(token);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (TokenExpiredException e) {
                // 5. Manejar token expirado (401 Unauthorized)
                handlerExceptionResolver.resolveException(request, response, null, new TkExpiredException("El token ha expirado"));
                return; // Detener la cadena de filtros
            } catch (Exception e) {
                // 6. Manejar cualquier otra excepción de token (inválido, malformado) (401 Unauthorized)
                handlerExceptionResolver.resolveException(request, response, null, new RuntimeException("Token inválido"));
                return; // Detener la cadena de filtros
            }
        } else {
            // 7. Manejar la ausencia de token en una ruta protegida (401 Unauthorized)
            // Si no hay token o no tiene el formato "Bearer ", y la ruta NO es pública,
            // lanzamos una AuthenticationException.
            handlerExceptionResolver.resolveException(request, response, null,
                    new AuthenticationException("Usted no tiene permisos para acceder a esta sesión. Se requiere un token de autenticación."));
            return; // Detener la cadena de filtros
        }

        // 8. Continuar con la cadena de filtros si el token es válido o la URL es pública
        filterChain.doFilter(request, response);
    }
}