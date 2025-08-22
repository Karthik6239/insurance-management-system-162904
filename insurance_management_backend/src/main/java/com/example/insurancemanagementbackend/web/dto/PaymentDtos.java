package com.example.insurancemanagementbackend.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PaymentDtos {

    public static class RecordPremiumPaymentRequest {
        // PUBLIC_INTERFACE
        @Schema(description = "CustomerPolicy id", example = "1001")
        @NotNull
        public Long customerPolicyId;

        // PUBLIC_INTERFACE
        @Schema(description = "Customer id", example = "20")
        @NotNull
        public Long customerId;

        // PUBLIC_INTERFACE
        @Schema(description = "Amount", example = "199.99")
        @NotNull @DecimalMin("0.0")
        public BigDecimal amount;

        // PUBLIC_INTERFACE
        @Schema(description = "Payment method", example = "CARD")
        @NotBlank
        public String method;
    }

    public static class RecordClaimPayoutRequest {
        // PUBLIC_INTERFACE
        @Schema(description = "Claim id", example = "555")
        @NotNull
        public Long claimId;

        // PUBLIC_INTERFACE
        @Schema(description = "CustomerPolicy id", example = "1001")
        @NotNull
        public Long customerPolicyId;

        // PUBLIC_INTERFACE
        @Schema(description = "Customer id", example = "20")
        @NotNull
        public Long customerId;

        // PUBLIC_INTERFACE
        @Schema(description = "Amount", example = "500.00")
        @NotNull @DecimalMin("0.0")
        public BigDecimal amount;

        // PUBLIC_INTERFACE
        @Schema(description = "Payment method", example = "BANK_TRANSFER")
        @NotBlank
        public String method;
    }
}
