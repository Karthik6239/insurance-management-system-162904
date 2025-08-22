package com.example.insurancemanagementbackend.web;

import com.example.insurancemanagementbackend.domain.Payment;
import com.example.insurancemanagementbackend.repository.ClaimRepository;
import com.example.insurancemanagementbackend.repository.PaymentRepository;
import com.example.insurancemanagementbackend.web.dto.ReportingDtos.ClaimsReportResponse;
import com.example.insurancemanagementbackend.web.dto.ReportingDtos.PremiumsReportResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * PUBLIC_INTERFACE
 * Reporting endpoints for admin dashboards.
 */
@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reports", description = "Reporting and analytics (ADMIN)")
public class ReportingController {

    @Autowired private ClaimRepository claimRepository;
    @Autowired private PaymentRepository paymentRepository;

    /**
     * PUBLIC_INTERFACE
     * Basic claims report with counts and total payouts.
     */
    @GetMapping("/claims")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Claims report", description = "Counts by status and total payouts")
    public ResponseEntity<ClaimsReportResponse> claimsReport() {
        long total = claimRepository.count();
        long approved = claimRepository.findAll().stream().filter(c -> "APPROVED".equalsIgnoreCase(c.getStatus())).count();
        long rejected = claimRepository.findAll().stream().filter(c -> "REJECTED".equalsIgnoreCase(c.getStatus())).count();
        long pending = claimRepository.findAll().stream().filter(c -> !"APPROVED".equalsIgnoreCase(c.getStatus()) && !"REJECTED".equalsIgnoreCase(c.getStatus())).count();

        BigDecimal totalPayout = paymentRepository.findAll().stream()
                .filter(p -> "OUT".equalsIgnoreCase(p.getDirection()))
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return ResponseEntity.ok(new ClaimsReportResponse(total, approved, rejected, pending, totalPayout));
    }

    /**
     * PUBLIC_INTERFACE
     * Premiums report aggregating inbound payments.
     */
    @GetMapping("/premiums")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Premiums report", description = "Total premiums collected and payment count")
    public ResponseEntity<PremiumsReportResponse> premiumsReport() {
        var payments = paymentRepository.findAll().stream().filter(p -> "IN".equalsIgnoreCase(p.getDirection())).toList();
        BigDecimal total = payments.stream().map(Payment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        long count = payments.size();
        return ResponseEntity.ok(new PremiumsReportResponse(total, count));
    }
}
