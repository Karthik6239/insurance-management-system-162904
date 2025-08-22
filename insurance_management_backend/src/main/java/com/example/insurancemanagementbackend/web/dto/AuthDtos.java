package com.example.insurancemanagementbackend.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

/**
 * DTOs used for authentication and user management requests/responses.
 */
public class AuthDtos {

    public static class LoginRequest {
        // PUBLIC_INTERFACE
        @Schema(description = "User email address", example = "user@example.com")
        @NotBlank @Email
        public String email;

        // PUBLIC_INTERFACE
        @Schema(description = "User password", example = "P@ssw0rd!")
        @NotBlank
        public String password;
    }

    public static class LoginResponse {
        // PUBLIC_INTERFACE
        @Schema(description = "JWT access token")
        public String token;

        // PUBLIC_INTERFACE
        @Schema(description = "Email of authenticated user")
        public String email;

        // PUBLIC_INTERFACE
        @Schema(description = "Granted roles")
        public Set<String> roles;

        public LoginResponse(String token, String email, Set<String> roles) {
            this.token = token;
            this.email = email;
            this.roles = roles;
        }
    }

    public static class RegisterRequest {
        // PUBLIC_INTERFACE
        @Schema(description = "Full name", example = "Jane Doe")
        @NotBlank
        public String fullName;

        // PUBLIC_INTERFACE
        @Schema(description = "Email", example = "jane@example.com")
        @NotBlank @Email
        public String email;

        // PUBLIC_INTERFACE
        @Schema(description = "Password")
        @NotBlank
        public String password;

        // PUBLIC_INTERFACE
        @Schema(description = "Initial roles (ADMIN/AGENT/CUSTOMER). If omitted, defaults to CUSTOMER.")
        public Set<String> roles;
    }

    public static class AssignRolesRequest {
        // PUBLIC_INTERFACE
        @Schema(description = "Role names to assign", example = "[\"ADMIN\",\"AGENT\"]")
        public Set<String> roles;
    }
}
