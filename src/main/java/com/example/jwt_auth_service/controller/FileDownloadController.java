package com.example.jwt_auth_service.controller;

import com.example.jwt_auth_service.config.JwtProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@RestController
@RequestMapping("/files")
@ConfigurationProperties(prefix = "jwt")

public class FileDownloadController {

    private final JwtProperties jwtProperties;

    public FileDownloadController(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }
    @GetMapping("/downloadFiles/{fileName}")

    public ResponseEntity<byte[]> publicDownloadPdfFiles(@PathVariable String fileName) throws IOException {

        Path filePath = Paths.get(jwtProperties.getFilesRoute(), fileName);

        try{

            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            byte[] fileBytes = Files.readAllBytes(filePath);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(fileBytes);

        } catch (IOException e) {
            System.err.println("Error: It can not retrieve the file");
        }
        return null;
    }



}
