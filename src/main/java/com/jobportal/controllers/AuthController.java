package com.jobportal.controllers;

import com.jobportal.dto.JwtResponse;
import com.jobportal.dto.LoginRequest;
import com.jobportal.dto.LoginResponse;
import com.jobportal.dto.SignUpRequest;
import com.jobportal.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api/auth")
    public class AuthController{
    private AuthService authService;
    AuthController(AuthService authService){
        this.authService =authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SignUpRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/verify")
    public ResponseEntity<JwtResponse> validateToken(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(authService.validate(token));
    }

}
