package com.example.insurancemanagementbackend.repository;

import com.example.insurancemanagementbackend.domain.Claim;
import com.example.insurancemanagementbackend.domain.CustomerPolicy;
import com.example.insurancemanagementbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Claim entity.
 */
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    // PUBLIC_INTERFACE
    /**
     * Find by unique reference.
     * @param reference claim reference
     * @return optional claim
     */
    Optional<Claim> findByReference(String reference);

    // PUBLIC_INTERFACE
    /**
     * Find claims for a customer policy.
     * @param customerPolicy policy instance
     * @return list of claims
     */
    List<Claim> findByCustomerPolicy(CustomerPolicy customerPolicy);

    // PUBLIC_INTERFACE
    /**
     * Find claims for a customer by current status.
     * @param customer user
     * @param status status string
     * @return list of claims
     */
    List<Claim> findByCustomerAndStatus(User customer, String status);
}
