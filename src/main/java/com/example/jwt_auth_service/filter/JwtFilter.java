package com.example.jwt_auth_service.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.jwt_auth_service.exception.TkExpiredException;
import com.example.jwt_auth_service.exception.AuthenticationException;
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
import java.util.Collections;

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
        //Public URLs
        boolean isPublicUrl = PUBLIC_URLS.stream().anyMatch(requestURI::startsWith);

        if (isPublicUrl) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {

                String username = jwtUtil.validateTokenAndRetrieveSubject(token);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (TokenExpiredException e) {
                // Handling (401 Unauthorized)
                handlerExceptionResolver.resolveException(request, response, null, new TkExpiredException("Token has been expired"));
                return; 
            } catch (Exception e) {
                // (Invalid Token, No format) (401 Unauthorized)
                handlerExceptionResolver.resolveException(request, response, null, new RuntimeException("Invalid Token"));
                return; 
            }
        } else {
            // Lack Token(401 Unauthorized)
            handlerExceptionResolver.resolveException(request, response, null,
                    new AuthenticationException("You do not have enougth permission to be here"));
            return; 
        }

        filterChain.doFilter(request, response);
    }
}