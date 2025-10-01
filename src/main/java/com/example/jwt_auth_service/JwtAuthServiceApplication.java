package com.example.jwt_auth_service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class JwtAuthServiceApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
        SpringApplication.run(JwtAuthServiceApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner run() {
//        return args -> {
//            for(int i = 0; i < 101; i++) {
//
//                String result = (i % 3 == 0) ? "Fizz" : "";
//                result += (i%5 == 0) ? "Buzz": "";
//                result += (i%3 == 0 && i%5 == 0) ? "FizzBuzz": "";
//                System.out.println((result.isEmpty()) ? i : result);
//            }
//        };
//    }
}
