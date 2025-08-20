package com.example.jwt_auth_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring")
public class JwtProperties {
    private String secret;
    private long expiration;


    private String filesRoute;

    // Getters y setters
    public String getSecret() {
        return secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpiration() {
        return expiration;
    }
    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }


    public String getFilesRoute() {
        return filesRoute;
    }


    public void setFilesRoute(String filesRoute) {
        this.filesRoute = filesRoute;
    }
}
