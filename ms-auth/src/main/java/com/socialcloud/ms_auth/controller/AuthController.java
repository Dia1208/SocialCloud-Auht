package com.socialcloud.ms_auth.controller;

import com.socialcloud.ms_auth.dto.AuthResponse;
import com.socialcloud.ms_auth.dto.LoginRequest;
import com.socialcloud.ms_auth.dto.RegisterRequest;
import com.socialcloud.ms_auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return service.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return service.login(request);
    }
}