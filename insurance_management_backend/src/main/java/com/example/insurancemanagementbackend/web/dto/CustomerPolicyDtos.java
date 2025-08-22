package com.example.insurancemanagementbackend.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomerPolicyDtos {

    public static class PurchasePolicyRequest {
        // PUBLIC_INTERFACE
        @Schema(description = "Customer user id", example = "10")
        @NotNull
        public Long customerId;

        // PUBLIC_INTERFACE
        @Schema(description = "Policy id", example = "5")
        @NotNull
        public Long policyId;

        // PUBLIC_INTERFACE
        @Schema(description = "Start date (YYYY-MM-DD)", example = "2025-01-01")
        @NotNull
        public LocalDate startDate;

        // PUBLIC_INTERFACE
        @Schema(description = "End date (YYYY-MM-DD)", example = "2025-12-31")
        public LocalDate endDate;

        // PUBLIC_INTERFACE
        @Schema(description = "Premium", example = "199.99")
        @NotNull @DecimalMin("0.0")
        public BigDecimal premium;
    }
}
