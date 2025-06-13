package com.mohan.inventory_management.controller;

import com.mohan.inventory_management.dto.ApiResponse;
import com.mohan.inventory_management.dto.LoginRequest;
import com.mohan.inventory_management.dto.LoginResponse;
import com.mohan.inventory_management.dto.RegisterRequest;
import com.mohan.inventory_management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
