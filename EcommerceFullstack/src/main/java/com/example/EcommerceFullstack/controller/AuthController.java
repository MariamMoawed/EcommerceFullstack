package com.example.EcommerceFullstack.controller;

import com.example.EcommerceFullstack.dto.AuthRequest;
import com.example.EcommerceFullstack.dto.AuthResponse;
import com.example.EcommerceFullstack.entity.Role;
import com.example.EcommerceFullstack.entity.User;
import com.example.EcommerceFullstack.repository.UserRepository;
import com.example.EcommerceFullstack.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authManager, JwtService jwtService, UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepo.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepo.save(user);
        return ResponseEntity.ok(new AuthResponse(jwtService.generateToken(user)));
    }
}
