package com.example.hzv1.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hzv1.model.ActiveToken;
import com.example.hzv1.model.LoginCred;
import com.example.hzv1.model.LoginRequest;
import com.example.hzv1.model.RevokedToken;
import com.example.hzv1.repository.ActiveTokenRepository;
import com.example.hzv1.repository.LoginCredRepository;
import com.example.hzv1.repository.RevokedTokenRepository;
import com.example.hzv1.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ActiveTokenRepository activeTokenRepository;
    private final RevokedTokenRepository revokedTokenRepository;
    private final LoginCredRepository loginCredRepository;
    private final JwtUtil jwtUtil;

    public AuthController(LoginCredRepository loginCredRepository, JwtUtil jwtUtil,
    		RevokedTokenRepository revokedTokenRepository,
    		ActiveTokenRepository activeTokenRepository) 
    {
        this.activeTokenRepository = activeTokenRepository;
        this.revokedTokenRepository = revokedTokenRepository;
        this.loginCredRepository = loginCredRepository;
        this.jwtUtil = jwtUtil;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> registerRequest) {
        String username = registerRequest.get("username");
        String password = registerRequest.get("password");

        // Check if username already exists
        if (loginCredRepository.existsByUsername(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Username already taken"));
        }

        // Save new user
        LoginCred newUser = new LoginCred();
        newUser.setUsername(username);
        newUser.setPassword(password);
        loginCredRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "User registered successfully"));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Optional<LoginCred> user = loginCredRepository.findByUsernameAndPassword(
            request.getUsername(), request.getPassword()
        );

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(Map.of("error", "Invalid username or password"));
        }

        // Check if user already has an active token
        Optional<ActiveToken> existingToken = activeTokenRepository.findByUsername(request.getUsername());
        if (existingToken.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body(Map.of("error", "User already logged in"));
        }

        // Generate new token
        String token = jwtUtil.generateToken(request.getUsername());
        activeTokenRepository.save(new ActiveToken(request.getUsername(), token, LocalDateTime.now()));

        return ResponseEntity.ok(Map.of("token", token));
    }


    
    @PostMapping("/logout")
    @Transactional
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Authorization token is required for logout"));
        }

        String token = authHeader.substring(7).trim();

        if (!activeTokenRepository.existsByToken(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Token is not active or already revoked"));
        }

        activeTokenRepository.deleteByToken(token);
        revokedTokenRepository.save(new RevokedToken(token));

        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
    
}