package com.example.insurancemanagementbackend.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ClaimDtos {

    public static class SubmitClaimRequest {
        // PUBLIC_INTERFACE
        @Schema(description = "CustomerPolicy id", example = "1001")
        @NotNull
        public Long customerPolicyId;

        // PUBLIC_INTERFACE
        @Schema(description = "Created by user id (agent or customer)", example = "10")
        @NotNull
        public Long createdByUserId;

        // PUBLIC_INTERFACE
        @Schema(description = "Customer user id who owns the policy", example = "20")
        @NotNull
        public Long customerId;

        // PUBLIC_INTERFACE
        @Schema(description = "Claim amount", example = "500.00")
        @NotNull @DecimalMin("0.0")
        public BigDecimal claimAmount;

        // PUBLIC_INTERFACE
        @Schema(description = "Description of the incident", example = "Hospitalization for 3 days")
        public String description;

        // PUBLIC_INTERFACE
        @Schema(description = "Public URL to claim attachment in Supabase storage (set by frontend after upload)", example = "https://project.supabase.co/storage/v1/object/public/claims/abc123.pdf")
        public String attachmentUrl;
    }

    public static class UpdateClaimStatusRequest {
        // PUBLIC_INTERFACE
        @Schema(description = "New status", example = "APPROVED")
        @NotBlank
        public String toStatus;

        // PUBLIC_INTERFACE
        @Schema(description = "Actor user id performing the action", example = "1")
        @NotNull
        public Long actorUserId;

        // PUBLIC_INTERFACE
        @Schema(description = "Optional notes", example = "Reviewed all documents")
        public String notes;
    }
}
