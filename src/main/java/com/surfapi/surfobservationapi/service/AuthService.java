package com.surfapi.surfobservationapi.service;

import com.surfapi.surfobservationapi.dto.AuthResponse;
import com.surfapi.surfobservationapi.dto.LoginRequest;
import com.surfapi.surfobservationapi.dto.RegisterRequest;
import com.surfapi.surfobservationapi.entity.User;
import com.surfapi.surfobservationapi.repository.UserRepository;
import com.surfapi.surfobservationapi.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // inject dependencies for database access, password hashing, and JWT handling
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // registers a new user and returns a JWT token
    public AuthResponse register(RegisterRequest request) {

        // prevent duplicate email registration
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("EMAIL_TAKEN");
        }

        // create and save new user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // hash password
                .build();

        userRepository.save(user);

        // generate JWT token for newly registered user
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());
        return new AuthResponse(token);
    }

    // logs user in and returns a JWT token
    public AuthResponse login(LoginRequest request) {

        // find user by email
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("INVALID_CREDENTIALS"));

        // check if password matches hashed password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("INVALID_CREDENTIALS");
        }

        // generate JWT token after successful login
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());
        return new AuthResponse(token);
    }
}
