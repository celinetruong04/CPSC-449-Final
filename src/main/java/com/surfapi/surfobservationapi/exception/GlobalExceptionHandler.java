package com.surfapi.surfobservationapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // handles custom runtime exceptions across the application
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        
        String message = ex.getMessage();

        // match exception messages to HTTP status codes
        HttpStatus status = switch (message) {
            case "EMAIL_TAKEN" -> HttpStatus.CONFLICT;
            case "INVALID_CREDENTIALS" -> HttpStatus.UNAUTHORIZED;
            case "Forbidden" -> HttpStatus.FORBIDDEN;
            case "Observation not found" -> HttpStatus.NOT_FOUND;
            case "User not found" -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        // standard error response body
        Map<String, Object> error = Map.of(
                "status", status.value(),
                "message", getFriendlyMessage(message),
                "timestamp", LocalDateTime.now().toString()
        );

        return ResponseEntity.status(status).body(error);
    }

    // handles validation errors from @Valid annotations
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {

        // get first valid error message
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Invalid request");

        Map<String, Object> error = Map.of(
                "status", 400,
                "message", message,
                "timestamp", LocalDateTime.now().toString()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // converts internal error codes into user-friendly messages
    private String getFriendlyMessage(String code) {
        return switch (code) {
            case "EMAIL_TAKEN" -> "An account with this email already exists.";
            case "INVALID_CREDENTIALS" -> "Invalid email or password.";
            case "Forbidden" -> "You do not have permission to access this resource.";
            case "Observation not found" -> "Observation not found.";
            case "User not found" -> "User not found.";
            default -> "An unexpected error occured";
        };
    }
}
