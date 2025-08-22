package com.example.insurancemanagementbackend.web;

import com.example.insurancemanagementbackend.domain.Payment;
import com.example.insurancemanagementbackend.service.PaymentService;
import com.example.insurancemanagementbackend.web.dto.PaymentDtos.RecordClaimPayoutRequest;
import com.example.insurancemanagementbackend.web.dto.PaymentDtos.RecordPremiumPaymentRequest;
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
 * Endpoints for recording payments and payouts.
 */
@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payments", description = "Premium payments and claim payouts")
public class PaymentController {

    @Autowired private PaymentService paymentService;

    /**
     * PUBLIC_INTERFACE
     * Record a premium payment (CUSTOMER/AGENT/ADMIN).
     */
    @PostMapping("/premium")
    @PreAuthorize("hasAnyRole('CUSTOMER','AGENT','ADMIN')")
    @Operation(summary = "Record premium", description = "Record a premium payment for a customer policy")
    public ResponseEntity<Payment> premium(@Valid @RequestBody RecordPremiumPaymentRequest req) {
        return ResponseEntity.ok(paymentService.recordPremiumPayment(req.customerPolicyId, req.customerId, req.amount, req.method));
    }

    /**
     * PUBLIC_INTERFACE
     * Record a claim payout (ADMIN/AGENT).
     */
    @PostMapping("/payout")
    @PreAuthorize("hasAnyRole('AGENT','ADMIN')")
    @Operation(summary = "Record payout", description = "Record a claim payout to a customer")
    public ResponseEntity<Payment> payout(@Valid @RequestBody RecordClaimPayoutRequest req) {
        return ResponseEntity.ok(paymentService.recordClaimPayout(req.claimId, req.customerPolicyId, req.customerId, req.amount, req.method));
    }

    /**
     * PUBLIC_INTERFACE
     * List payments for a customer.
     */
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyRole('CUSTOMER','AGENT','ADMIN')")
    @Operation(summary = "Payments for customer", description = "List all payments for a customer")
    public ResponseEntity<List<Payment>> listForCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(paymentService.listForCustomer(customerId));
    }
}
