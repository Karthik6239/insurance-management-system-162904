package com.example.insurancemanagementbackend.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class ReportingDtos {

    public static class ClaimsReportResponse {
        // PUBLIC_INTERFACE
        @Schema(description = "Total number of claims")
        public long totalClaims;

        // PUBLIC_INTERFACE
        @Schema(description = "Number approved")
        public long approved;

        // PUBLIC_INTERFACE
        @Schema(description = "Number rejected")
        public long rejected;

        // PUBLIC_INTERFACE
        @Schema(description = "Number pending (submitted/in_review)")
        public long pending;

        // PUBLIC_INTERFACE
        @Schema(description = "Total payout amount")
        public BigDecimal totalPayout;

        public ClaimsReportResponse(long totalClaims, long approved, long rejected, long pending, BigDecimal totalPayout) {
            this.totalClaims = totalClaims;
            this.approved = approved;
            this.rejected = rejected;
            this.pending = pending;
            this.totalPayout = totalPayout;
        }
    }

    public static class PremiumsReportResponse {
        // PUBLIC_INTERFACE
        @Schema(description = "Total premiums collected")
        public BigDecimal totalPremiums;

        // PUBLIC_INTERFACE
        @Schema(description = "Number of payments")
        public long paymentsCount;

        public PremiumsReportResponse(BigDecimal totalPremiums, long paymentsCount) {
            this.totalPremiums = totalPremiums;
            this.paymentsCount = paymentsCount;
        }
    }
}
