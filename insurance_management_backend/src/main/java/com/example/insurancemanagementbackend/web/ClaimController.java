package com.example.insurancemanagementbackend.web;

import com.example.insurancemanagementbackend.domain.AuditEntry;
import com.example.insurancemanagementbackend.domain.Claim;
import com.example.insurancemanagementbackend.service.AuditService;
import com.example.insurancemanagementbackend.service.ClaimService;
import com.example.insurancemanagementbackend.web.dto.ClaimDtos.SubmitClaimRequest;
import com.example.insurancemanagementbackend.web.dto.ClaimDtos.UpdateClaimStatusRequest;
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
 * Endpoints for submitting and managing claims.
 */
@RestController
@RequestMapping("/api/claims")
@Tag(name = "Claims", description = "Claims submission and processing endpoints")
public class ClaimController {

    @Autowired private ClaimService claimService;
    @Autowired private AuditService auditService;

    /**
     * PUBLIC_INTERFACE
     * Submit a new claim. If attachmentUrl is provided, it will be stored in an AuditEntry.
     * Frontend is responsible for uploading file to Supabase and passing public URL here.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER','AGENT','ADMIN')")
    @Operation(summary = "Submit claim", description = "Submit a new claim against a customer policy. Attachment URL handled via Supabase.")
    public ResponseEntity<Claim> submit(@Valid @RequestBody SubmitClaimRequest req) {
        Claim c = claimService.submitClaim(req.customerPolicyId, req.createdByUserId, req.customerId, req.claimAmount, req.description);
        if (req.attachmentUrl != null && !req.attachmentUrl.isBlank()) {
            AuditEntry ae = auditService.audit(req.createdByUserId, "ATTACHMENT_ADDED", "Claim", c.getId(), "attachmentUrl=" + req.attachmentUrl);
        }
        return ResponseEntity.ok(c);
    }

    /**
     * PUBLIC_INTERFACE
     * Update claim status (AGENT/ADMIN).
     */
    @PostMapping("/{claimId}/status")
    @PreAuthorize("hasAnyRole('AGENT','ADMIN')")
    @Operation(summary = "Update claim status", description = "Transition claim status and record history")
    public ResponseEntity<Claim> updateStatus(@PathVariable Long claimId, @Valid @RequestBody UpdateClaimStatusRequest req) {
        return ResponseEntity.ok(claimService.updateStatus(claimId, req.toStatus, req.actorUserId, req.notes));
    }

    /**
     * PUBLIC_INTERFACE
     * Fetch claim by reference.
     */
    @GetMapping("/ref/{reference}")
    @Operation(summary = "Find by reference", description = "Fetch a claim by its unique reference")
    public ResponseEntity<Claim> byReference(@PathVariable String reference) {
        return ResponseEntity.of(claimService.findByReference(reference));
    }

    /**
     * PUBLIC_INTERFACE
     * List claims for a customer.
     */
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyRole('CUSTOMER','AGENT','ADMIN')")
    @Operation(summary = "Claims for customer", description = "List all claims for a customer")
    public ResponseEntity<List<Claim>> forCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(claimService.listForCustomer(customerId));
    }
}
