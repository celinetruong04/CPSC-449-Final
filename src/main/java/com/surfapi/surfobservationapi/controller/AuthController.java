package com.surfapi.surfobservationapi.controller;

import com.surfapi.surfobservationapi.dto.AuthResponse;
import com.surfapi.surfobservationapi.dto.LoginRequest;
import com.surfapi.surfobservationapi.dto.RegisterRequest;
import com.surfapi.surfobservationapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // inject authentication service
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // register a new user and return JWT token
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // login existing user and return JWT token
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}