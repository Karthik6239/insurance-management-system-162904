package com.example.insurancemanagementbackend.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * PUBLIC_INTERFACE
 * Current user info and role-restricted sample endpoints.
 */
@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "Authenticated user endpoints")
public class UserController {

    /**
     * PUBLIC_INTERFACE
     * Returns the current authenticated principal username.
     */
    @GetMapping("/me")
    @Operation(summary = "Current user info", description = "Returns the authenticated principal username")
    public ResponseEntity<String> me(Authentication authentication) {
        return ResponseEntity.ok(authentication.getName());
    }

    /**
     * PUBLIC_INTERFACE
     * Example endpoint for CUSTOMER role.
     */
    @GetMapping("/customer-only")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Customer-only example", description = "Example endpoint restricted to CUSTOMER role")
    public ResponseEntity<String> customerOnly() {
        return ResponseEntity.ok("Hello CUSTOMER!");
    }
}
