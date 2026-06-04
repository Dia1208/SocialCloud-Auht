package com.socialcloud.ms_auth.service;

import com.socialcloud.ms_auth.dto.AuthResponse;
import com.socialcloud.ms_auth.dto.LoginRequest;
import com.socialcloud.ms_auth.dto.RegisterRequest;
import com.socialcloud.ms_auth.model.AuthUser;
import com.socialcloud.ms_auth.repository.AuthUserRepository;
import com.socialcloud.ms_auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        AuthUser user = AuthUser.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        repository.save(user);

        return new AuthResponse(jwtService.generateToken(user.getEmail()));
    }

    public AuthResponse login(LoginRequest request) {
        AuthUser user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return new AuthResponse(jwtService.generateToken(user.getEmail()));
    }
}