package com.example.insurancemanagementbackend.repository;

import com.example.insurancemanagementbackend.domain.Policy;
import com.example.insurancemanagementbackend.domain.enums.PolicyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Policy entity.
 */
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    // PUBLIC_INTERFACE
    /**
     * Find by code.
     * @param code unique policy code
     * @return optional policy
     */
    Optional<Policy> findByCode(String code);

    // PUBLIC_INTERFACE
    /**
     * Find policies by type.
     * @param type policy type
     * @return list of policies
     */
    List<Policy> findByType(PolicyType type);
}
