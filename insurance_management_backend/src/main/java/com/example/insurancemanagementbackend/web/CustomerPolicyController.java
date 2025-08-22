package com.example.insurancemanagementbackend.web;

import com.example.insurancemanagementbackend.domain.CustomerPolicy;
import com.example.insurancemanagementbackend.service.CustomerPolicyService;
import com.example.insurancemanagementbackend.web.dto.CustomerPolicyDtos.PurchasePolicyRequest;
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
 * Endpoints for managing customer policies (purchase, cancel, list).
 */
@RestController
@RequestMapping("/api/customer-policies")
@Tag(name = "Customer Policies", description = "Purchase and manage customer policies")
public class CustomerPolicyController {

    @Autowired private CustomerPolicyService customerPolicyService;

    /**
     * PUBLIC_INTERFACE
     * Purchase a policy (CUSTOMER or AGENT).
     */
    @PostMapping("/purchase")
    @PreAuthorize("hasAnyRole('CUSTOMER','AGENT','ADMIN')")
    @Operation(summary = "Purchase policy", description = "Purchase a policy for a customer")
    public ResponseEntity<CustomerPolicy> purchase(@Valid @RequestBody PurchasePolicyRequest req) {
        CustomerPolicy cp = customerPolicyService.purchasePolicy(req.customerId, req.policyId, req.startDate, req.endDate, req.premium);
        return ResponseEntity.ok(cp);
    }

    /**
     * PUBLIC_INTERFACE
     * Cancel a customer policy (ADMIN or AGENT).
     */
    @PostMapping("/{customerPolicyId}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    @Operation(summary = "Cancel policy", description = "Cancel a customer policy by id")
    public ResponseEntity<CustomerPolicy> cancel(@PathVariable Long customerPolicyId) {
        return ResponseEntity.ok(customerPolicyService.cancelPolicy(customerPolicyId));
    }

    /**
     * PUBLIC_INTERFACE
     * List policies for a customer.
     */
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyRole('CUSTOMER','AGENT','ADMIN')")
    @Operation(summary = "List for customer", description = "List all policies for a given customer")
    public ResponseEntity<List<CustomerPolicy>> listForCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerPolicyService.listForCustomer(customerId));
    }
}
