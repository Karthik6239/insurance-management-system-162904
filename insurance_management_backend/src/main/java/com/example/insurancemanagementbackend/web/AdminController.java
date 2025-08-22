package com.example.insurancemanagementbackend.web;

import com.example.insurancemanagementbackend.domain.Role;
import com.example.insurancemanagementbackend.domain.User;
import com.example.insurancemanagementbackend.repository.UserRepository;
import com.example.insurancemanagementbackend.service.UserService;
import com.example.insurancemanagementbackend.web.dto.AuthDtos.AssignRolesRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PUBLIC_INTERFACE
 * Admin endpoints for managing roles and basic user state.
 */
@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "Admin role management and protected endpoints")
public class AdminController {

    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;

    /**
     * PUBLIC_INTERFACE
     * Lists all roles present in the system.
     * @return list of roles
     */
    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "List roles", description = "ADMIN only: list all roles")
    public ResponseEntity<List<Role>> listRoles() {
        return ResponseEntity.ok(userService.listRoles());
    }

    /**
     * PUBLIC_INTERFACE
     * Assign roles to a user by id.
     * @param userId target user
     * @param req roles wrapper
     * @return updated user
     */
    @PostMapping("/users/{userId}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Assign roles", description = "ADMIN only: assign roles to a user")
    public ResponseEntity<User> assignRoles(@PathVariable Long userId, @Valid @RequestBody AssignRolesRequest req) {
        return ResponseEntity.ok(userService.assignRoles(userId, req.roles));
    }

    /**
     * PUBLIC_INTERFACE
     * Enable or disable a user account.
     * @param userId id
     * @param enabled flag
     * @return updated user
     */
    @PostMapping("/users/{userId}/enabled/{enabled}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Toggle user enabled", description = "ADMIN only: enable or disable a user")
    public ResponseEntity<User> toggleUser(@PathVariable Long userId, @PathVariable boolean enabled) {
        return ResponseEntity.ok(userService.setEnabled(userId, enabled));
    }

    /**
     * PUBLIC_INTERFACE
     * Example protected endpoint for agents only.
     */
    @GetMapping("/agent-only")
    @PreAuthorize("hasRole('AGENT')")
    @Operation(summary = "Agent-only example", description = "Example endpoint restricted to AGENT role")
    public ResponseEntity<String> agentOnly() {
        return ResponseEntity.ok("Hello AGENT!");
    }
}
