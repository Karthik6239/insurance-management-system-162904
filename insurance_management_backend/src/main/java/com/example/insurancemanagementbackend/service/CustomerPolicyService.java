package com.example.insurancemanagementbackend.service;

import com.example.insurancemanagementbackend.domain.CustomerPolicy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * PUBLIC_INTERFACE
 * CustomerPolicyService defines operations for purchasing and managing customer policies.
 */
public interface CustomerPolicyService {

    /**
     * Purchase a policy for a customer.
     * @param customerId user id
     * @param policyId policy id
     * @param start start date
     * @param end end date
     * @param premium premium amount
     * @return created link
     */
    CustomerPolicy purchasePolicy(Long customerId, Long policyId, LocalDate start, LocalDate end, BigDecimal premium);

    /**
     * Cancel a customer policy.
     * @param customerPolicyId id
     * @return updated entity (inactive)
     */
    CustomerPolicy cancelPolicy(Long customerPolicyId);

    /**
     * List policies for a customer.
     * @param customerId id
     * @return list
     */
    List<CustomerPolicy> listForCustomer(Long customerId);
}
