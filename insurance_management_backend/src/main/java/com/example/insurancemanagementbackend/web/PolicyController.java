package com.example.insurancemanagementbackend.web;

import com.example.insurancemanagementbackend.domain.Policy;
import com.example.insurancemanagementbackend.domain.enums.PolicyType;
import com.example.insurancemanagementbackend.service.PolicyService;
import com.example.insurancemanagementbackend.web.dto.PolicyDtos.CreatePolicyRequest;
import com.example.insurancemanagementbackend.web.dto.PolicyDtos.UpdatePremiumRequest;
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
 * Endpoints for managing policy products.
 */
@RestController
@RequestMapping("/api/policies")
@Tag(name = "Policies", description = "Policy management endpoints")
public class PolicyController {

    @Autowired private PolicyService policyService;

    /**
     * PUBLIC_INTERFACE
     * Create a new policy product (ADMIN only).
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create policy", description = "ADMIN: Create new policy product")
    public ResponseEntity<Policy> create(@Valid @RequestBody CreatePolicyRequest req) {
        Policy p = policyService.createPolicy(req.code, req.name, req.type, req.basePremium);
        if (req.coverageAmount != null) p.setCoverageAmount(req.coverageAmount);
        if (req.description != null) p.setDescription(req.description);
        return ResponseEntity.ok(p);
    }

    /**
     * PUBLIC_INTERFACE
     * Update base premium for a policy (ADMIN only).
     */
    @PostMapping("/{policyId}/premium")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update policy premium", description = "ADMIN: Update base premium")
    public ResponseEntity<Policy> updatePremium(@PathVariable Long policyId, @Valid @RequestBody UpdatePremiumRequest req) {
        return ResponseEntity.ok(policyService.updatePremium(policyId, req.basePremium));
    }

    /**
     * PUBLIC_INTERFACE
     * List all policies (any authenticated user).
     */
    @GetMapping
    @Operation(summary = "List policies", description = "List all policy products")
    public ResponseEntity<List<Policy>> listAll() {
        return ResponseEntity.ok(policyService.listAll());
    }

    /**
     * PUBLIC_INTERFACE
     * Filter by policy type.
     */
    @GetMapping("/type/{type}")
    @Operation(summary = "Policies by type", description = "List policies filtered by type")
    public ResponseEntity<List<Policy>> byType(@PathVariable PolicyType type) {
        return ResponseEntity.ok(policyService.findByType(type));
    }
}
