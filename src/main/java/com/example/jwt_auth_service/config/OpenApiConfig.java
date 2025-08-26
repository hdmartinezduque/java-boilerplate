package com.example.jwt_auth_service.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API example con Spring Boot y JWT")
                        .version("1.0")
                        .description("Automatic documentation for the API using OpenAPI 3.")
                        .contact(new Contact()
                                .name("Backend Team")
                                .email("soporte@empresa.com")
                                .url("""
                                        https://empresa.com""")));
    }
}