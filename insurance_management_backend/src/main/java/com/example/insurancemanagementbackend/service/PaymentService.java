package com.example.insurancemanagementbackend.service;

import com.example.insurancemanagementbackend.domain.Payment;

import java.math.BigDecimal;
import java.util.List;

/**
 * PUBLIC_INTERFACE
 * PaymentService defines operations for recording premium payments and payouts.
 */
public interface PaymentService {

    /**
     * Record a premium payment from a customer for a policy.
     * @param customerPolicyId id
     * @param customerId user id
     * @param amount amount
     * @param method method
     * @return created payment
     */
    Payment recordPremiumPayment(Long customerPolicyId, Long customerId, BigDecimal amount, String method);

    /**
     * Record a claim payout to a customer.
     * @param claimId claim id
     * @param customerPolicyId policy id
     * @param customerId user id
     * @param amount amount
     * @param method method
     * @return created payment
     */
    Payment recordClaimPayout(Long claimId, Long customerPolicyId, Long customerId, BigDecimal amount, String method);

    /**
     * List payments for a customer.
     * @param customerId id
     * @return list
     */
    List<Payment> listForCustomer(Long customerId);
}
