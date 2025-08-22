package com.example.insurancemanagementbackend.service.impl;

import com.example.insurancemanagementbackend.domain.*;
import com.example.insurancemanagementbackend.repository.*;
import com.example.insurancemanagementbackend.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of ClaimService with status history tracking.
 * Attachment URLs are recorded as an AuditEntry detail for traceability without schema changes.
 */
@Service
@Transactional
public class ClaimServiceImpl implements ClaimService {

    @Autowired private ClaimRepository claimRepository;
    @Autowired private ClaimStatusHistoryRepository statusHistoryRepository;
    @Autowired private CustomerPolicyRepository customerPolicyRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private AuditEntryRepository auditEntryRepository;

    // PUBLIC_INTERFACE
    @Override
    public Claim submitClaim(Long customerPolicyId, Long createdByUserId, Long customerId, BigDecimal amount, String description) {
        CustomerPolicy cp = customerPolicyRepository.findById(customerPolicyId).orElseThrow();
        User createdBy = userRepository.findById(createdByUserId).orElseThrow();
        User customer = userRepository.findById(customerId).orElseThrow();

        Claim claim = new Claim()
                .setReference("CLM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .setCustomerPolicy(cp)
                .setCreatedBy(createdBy)
                .setCustomer(customer)
                .setClaimAmount(amount)
                .setDescription(description)
                .setStatus("SUBMITTED")
                .setCreatedAt(Instant.now())
                .setUpdatedAt(Instant.now());
        Claim saved = claimRepository.save(claim);

        ClaimStatusHistory hist = new ClaimStatusHistory()
                .setClaim(saved)
                .setActor(createdBy)
                .setFromStatus(null)
                .setToStatus("SUBMITTED")
                .setNotes("Initial submission");
        statusHistoryRepository.save(hist);

        audit("CREATE", "Claim", saved.getId(), createdBy, "Claim submitted with reference " + saved.getReference());
        return saved;
    }

    // PUBLIC_INTERFACE
    @Override
    public Claim updateStatus(Long claimId, String toStatus, Long actorUserId, String notes) {
        Claim claim = claimRepository.findById(claimId).orElseThrow();
        User actor = userRepository.findById(actorUserId).orElseThrow();
        String from = claim.getStatus();
        claim.setStatus(toStatus);
        Claim saved = claimRepository.save(claim);

        ClaimStatusHistory hist = new ClaimStatusHistory()
                .setClaim(saved)
                .setActor(actor)
                .setFromStatus(from)
                .setToStatus(toStatus)
                .setNotes(notes);
        statusHistoryRepository.save(hist);

        audit("STATUS_CHANGE", "Claim", saved.getId(), actor, "from=" + from + ", to=" + toStatus + (notes != null ? " notes=" + notes : ""));
        return saved;
    }

    // PUBLIC_INTERFACE
    @Override
    @Transactional(readOnly = true)
    public Optional<Claim> findByReference(String reference) {
        return claimRepository.findByReference(reference);
    }

    // PUBLIC_INTERFACE
    @Override
    @Transactional(readOnly = true)
    public List<Claim> listForCustomer(Long customerId) {
        User customer = userRepository.findById(customerId).orElseThrow();
        return claimRepository.findByCustomerAndStatus(customer, customer.isEnabled() ? "SUBMITTED" : ""); // basic sample filter
    }

    private void audit(String action, String entityType, Long entityId, User actor, String details) {
        AuditEntry entry = new AuditEntry()
                .setActor(actor)
                .setAction(action)
                .setEntityType(entityType)
                .setEntityId(entityId)
                .setDetails(details)
                .setCreatedAt(Instant.now());
        auditEntryRepository.save(entry);
    }
}
