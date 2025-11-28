package com.yougiftremake.yougift.controller.authentication;

import org.springframework.web.bind.annotation.*;

import com.yougiftremake.yougift.dto.authentication.*;
import com.yougiftremake.yougift.dto.user.*;
import com.yougiftremake.yougift.service.authentication.AuthService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserCreateRequest createRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(createRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

}
