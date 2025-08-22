package com.example.insurancemanagementbackend.service;

import com.example.insurancemanagementbackend.domain.Policy;
import com.example.insurancemanagementbackend.domain.enums.PolicyType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * PUBLIC_INTERFACE
 * PolicyService defines operations related to policy products.
 */
public interface PolicyService {

    /**
     * Create policy product.
     * @param code unique code
     * @param name name
     * @param type type
     * @param basePremium base premium
     * @return created policy
     */
    Policy createPolicy(String code, String name, PolicyType type, BigDecimal basePremium);

    /**
     * Update policy pricing details.
     * @param policyId id
     * @param basePremium new base premium
     * @return updated policy
     */
    Policy updatePremium(Long policyId, BigDecimal basePremium);

    /**
     * Find by code.
     * @param code code
     * @return policy if present
     */
    Optional<Policy> findByCode(String code);

    /**
     * Find by type.
     * @param type policy type
     * @return list
     */
    List<Policy> findByType(PolicyType type);

    /**
     * List all policies.
     * @return list
     */
    List<Policy> listAll();
}
