package com.example.insurancemanagementbackend.service;

import com.example.insurancemanagementbackend.domain.Claim;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * PUBLIC_INTERFACE
 * ClaimService defines operations for submitting and processing claims.
 */
public interface ClaimService {

    /**
     * Submit a new claim.
     * @param customerPolicyId policy id
     * @param createdByUserId user who submitted
     * @param customerId customer
     * @param amount claim amount
     * @param description description
     * @return created claim
     */
    Claim submitClaim(Long customerPolicyId, Long createdByUserId, Long customerId, BigDecimal amount, String description);

    /**
     * Transition claim status and record history.
     * @param claimId claim id
     * @param toStatus new status
     * @param actorUserId actor id
     * @param notes notes
     * @return updated claim
     */
    Claim updateStatus(Long claimId, String toStatus, Long actorUserId, String notes);

    /**
     * Find by reference.
     * @param reference ref
     * @return optional claim
     */
    Optional<Claim> findByReference(String reference);

    /**
     * List claims for a customer.
     * @param customerId user id
     * @return list
     */
    List<Claim> listForCustomer(Long customerId);
}
