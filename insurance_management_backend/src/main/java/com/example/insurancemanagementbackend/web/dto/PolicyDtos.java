package com.example.insurancemanagementbackend.web.dto;

import com.example.insurancemanagementbackend.domain.enums.PolicyType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

// DTOs for Policy endpoints
public class PolicyDtos {

    public static class CreatePolicyRequest {
        // PUBLIC_INTERFACE
        @Schema(description = "Unique policy code", example = "HLT-001")
        @NotBlank
        public String code;

        // PUBLIC_INTERFACE
        @Schema(description = "Display name of policy", example = "Standard Health")
        @NotBlank
        public String name;

        // PUBLIC_INTERFACE
        @Schema(description = "Policy type", example = "HEALTH")
        @NotNull
        public PolicyType type;

        // PUBLIC_INTERFACE
        @Schema(description = "Base premium for policy", example = "199.99")
        @NotNull @DecimalMin("0.0")
        public BigDecimal basePremium;

        // PUBLIC_INTERFACE
        @Schema(description = "Coverage amount", example = "10000")
        public BigDecimal coverageAmount;

        // PUBLIC_INTERFACE
        @Schema(description = "Description")
        public String description;
    }

    public static class UpdatePremiumRequest {
        // PUBLIC_INTERFACE
        @Schema(description = "New base premium", example = "249.00")
        @NotNull @DecimalMin("0.0")
        public BigDecimal basePremium;
    }
}
