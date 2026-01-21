package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.dto.LoginRequest;
import com.example.TaskManagementSystem.dto.RegisterRequest;
import com.example.TaskManagementSystem.entity.User;
import com.example.TaskManagementSystem.repository.UserRepository;
import com.example.TaskManagementSystem.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    // api dang ki /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(token);
    }

    //api lay tat ca user chi cho phep admin truy cap
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        //thuc te nen goi qua service truoc, demo goi thang repository
        return ResponseEntity.ok(userRepository.findAll());
    }
}
