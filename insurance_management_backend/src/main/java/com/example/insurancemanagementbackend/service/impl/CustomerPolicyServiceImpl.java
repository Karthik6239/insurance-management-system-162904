package com.example.insurancemanagementbackend.service.impl;

import com.example.insurancemanagementbackend.domain.CustomerPolicy;
import com.example.insurancemanagementbackend.domain.Policy;
import com.example.insurancemanagementbackend.domain.User;
import com.example.insurancemanagementbackend.repository.CustomerPolicyRepository;
import com.example.insurancemanagementbackend.repository.PolicyRepository;
import com.example.insurancemanagementbackend.repository.UserRepository;
import com.example.insurancemanagementbackend.service.CustomerPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of CustomerPolicyService for purchasing and managing customer policies.
 */
@Service
@Transactional
public class CustomerPolicyServiceImpl implements CustomerPolicyService {

    @Autowired private CustomerPolicyRepository customerPolicyRepository;
    @Autowired private PolicyRepository policyRepository;
    @Autowired private UserRepository userRepository;

    // PUBLIC_INTERFACE
    @Override
    public CustomerPolicy purchasePolicy(Long customerId, Long policyId, LocalDate start, LocalDate end, BigDecimal premium) {
        User customer = userRepository.findById(customerId).orElseThrow();
        Policy policy = policyRepository.findById(policyId).orElseThrow();
        CustomerPolicy cp = new CustomerPolicy()
                .setCustomer(customer)
                .setPolicy(policy)
                .setStartDate(start)
                .setEndDate(end)
                .setPremium(premium)
                .setActive(true);
        return customerPolicyRepository.save(cp);
    }

    // PUBLIC_INTERFACE
    @Override
    public CustomerPolicy cancelPolicy(Long customerPolicyId) {
        CustomerPolicy cp = customerPolicyRepository.findById(customerPolicyId).orElseThrow();
        cp.setActive(false);
        return customerPolicyRepository.save(cp);
    }

    // PUBLIC_INTERFACE
    @Override
    @Transactional(readOnly = true)
    public List<CustomerPolicy> listForCustomer(Long customerId) {
        User customer = userRepository.findById(customerId).orElseThrow();
        return customerPolicyRepository.findByCustomer(customer);
    }
}
