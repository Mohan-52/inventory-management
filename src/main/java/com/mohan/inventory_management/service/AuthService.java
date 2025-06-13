package com.mohan.inventory_management.service;


import com.mohan.inventory_management.entity.User;
import com.mohan.inventory_management.dto.*;
import com.mohan.inventory_management.repository.UserRepository;
import com.mohan.inventory_management.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<ApiResponse> register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Email already exists", null));
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole().toLowerCase());

        user = userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse("Registration successful", user.getUserId()));
    }

    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail()).get();
        String token = jwtUtil.generateToken(user.getEmail(),user.getRole());

        return ResponseEntity.ok(new LoginResponse(token, user.getUserId(), user.getRole()));
    }
}
