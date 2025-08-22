package com.example.insurancemanagementbackend.web;

import com.example.insurancemanagementbackend.domain.User;
import com.example.insurancemanagementbackend.security.JwtService;
import com.example.insurancemanagementbackend.service.UserService;
import com.example.insurancemanagementbackend.web.dto.AuthDtos.LoginRequest;
import com.example.insurancemanagementbackend.web.dto.AuthDtos.LoginResponse;
import com.example.insurancemanagementbackend.web.dto.AuthDtos.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * PUBLIC_INTERFACE
 * Authentication routes: login and register. Uses JWT stateless session model.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private JwtService jwtService;
    @Autowired private UserService userService;

    /**
     * PUBLIC_INTERFACE
     * Login with email/password and receive a JWT token.
     * @param request LoginRequest containing email and password
     * @return LoginResponse with token and roles
     */
    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticate using email/password; returns JWT token")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email, request.password)
        );
        UserDetails details = userDetailsService.loadUserByUsername(request.email);
        String token = jwtService.generateToken(details);
        Set<String> roles = details.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toSet());
        return ResponseEntity.ok(new LoginResponse(token, details.getUsername(), roles));
    }

    /**
     * PUBLIC_INTERFACE
     * Register a new user; defaults to CUSTOMER role if roles omitted.
     * @param request RegisterRequest
     * @return 200 with token and basic info
     */
    @PostMapping("/register")
    @Operation(summary = "Register", description = "Create a new user account; defaults to CUSTOMER role")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.createUser(request.fullName, request.email, request.password, request.roles);
        UserDetails details = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(details);
        Set<String> roles = details.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toSet());
        return ResponseEntity.ok(new LoginResponse(token, details.getUsername(), roles));
    }
}
