package com.example.githubapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GithubUserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(GithubUserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "status", HttpStatus.NOT_FOUND.value(),
                        "message", e.getMessage()
                ));
    }
}
