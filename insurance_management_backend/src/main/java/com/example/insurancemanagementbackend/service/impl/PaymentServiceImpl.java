package com.example.insurancemanagementbackend.service.impl;

import com.example.insurancemanagementbackend.domain.*;
import com.example.insurancemanagementbackend.repository.*;
import com.example.insurancemanagementbackend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of PaymentService for recording premiums and payouts.
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired private PaymentRepository paymentRepository;
    @Autowired private CustomerPolicyRepository customerPolicyRepository;
    @Autowired private ClaimRepository claimRepository;
    @Autowired private UserRepository userRepository;

    // PUBLIC_INTERFACE
    @Override
    public Payment recordPremiumPayment(Long customerPolicyId, Long customerId, BigDecimal amount, String method) {
        CustomerPolicy cp = customerPolicyRepository.findById(customerPolicyId).orElseThrow();
        User customer = userRepository.findById(customerId).orElseThrow();

        Payment p = new Payment()
                .setReference("PMT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .setCustomerPolicy(cp)
                .setCustomer(customer)
                .setAmount(amount)
                .setMethod(method)
                .setDirection("IN")
                .setCreatedAt(Instant.now());
        return paymentRepository.save(p);
    }

    // PUBLIC_INTERFACE
    @Override
    public Payment recordClaimPayout(Long claimId, Long customerPolicyId, Long customerId, BigDecimal amount, String method) {
        Claim claim = claimRepository.findById(claimId).orElseThrow();
        CustomerPolicy cp = customerPolicyRepository.findById(customerPolicyId).orElseThrow();
        User customer = userRepository.findById(customerId).orElseThrow();

        Payment p = new Payment()
                .setReference("OUT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .setClaim(claim)
                .setCustomerPolicy(cp)
                .setCustomer(customer)
                .setAmount(amount)
                .setMethod(method)
                .setDirection("OUT")
                .setCreatedAt(Instant.now());
        return paymentRepository.save(p);
    }

    // PUBLIC_INTERFACE
    @Override
    @Transactional(readOnly = true)
    public List<Payment> listForCustomer(Long customerId) {
        User customer = userRepository.findById(customerId).orElseThrow();
        return paymentRepository.findByCustomer(customer);
    }
}
