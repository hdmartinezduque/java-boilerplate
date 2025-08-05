package com.example.jwt_auth_service.exception;

public class TkExpiredException extends RuntimeException {
    public TkExpiredException(String message) {
        super(message);
    }
}