package com.example.insurancemanagementbackend.service.impl;

import com.example.insurancemanagementbackend.domain.Policy;
import com.example.insurancemanagementbackend.domain.enums.PolicyType;
import com.example.insurancemanagementbackend.repository.PolicyRepository;
import com.example.insurancemanagementbackend.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of PolicyService for CRUD-like policy management.
 */
@Service
@Transactional
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    // PUBLIC_INTERFACE
    @Override
    public Policy createPolicy(String code, String name, PolicyType type, BigDecimal basePremium) {
        if (policyRepository.findByCode(code).isPresent()) {
            throw new IllegalArgumentException("Policy code already exists");
        }
        Policy p = new Policy()
                .setCode(code)
                .setName(name)
                .setType(type)
                .setBasePremium(basePremium);
        return policyRepository.save(p);
    }

    // PUBLIC_INTERFACE
    @Override
    public Policy updatePremium(Long policyId, BigDecimal basePremium) {
        Policy p = policyRepository.findById(policyId).orElseThrow();
        p.setBasePremium(basePremium);
        return policyRepository.save(p);
    }

    // PUBLIC_INTERFACE
    @Override
    @Transactional(readOnly = true)
    public Optional<Policy> findByCode(String code) {
        return policyRepository.findByCode(code);
    }

    // PUBLIC_INTERFACE
    @Override
    @Transactional(readOnly = true)
    public List<Policy> findByType(PolicyType type) {
        return policyRepository.findByType(type);
    }

    // PUBLIC_INTERFACE
    @Override
    @Transactional(readOnly = true)
    public List<Policy> listAll() {
        return policyRepository.findAll();
    }
}
