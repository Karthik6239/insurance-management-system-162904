package com.example.insurancemanagementbackend.repository;

import com.example.insurancemanagementbackend.domain.Payment;
import com.example.insurancemanagementbackend.domain.CustomerPolicy;
import com.example.insurancemanagementbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Payment entity.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // PUBLIC_INTERFACE
    /**
     * Find by unique payment reference.
     * @param reference reference
     * @return optional payment
     */
    Optional<Payment> findByReference(String reference);

    // PUBLIC_INTERFACE
    /**
     * Find all payments for a customer.
     * @param customer user
     * @return list of payments
     */
    List<Payment> findByCustomer(User customer);

    // PUBLIC_INTERFACE
    /**
     * Find all payments for a customer policy.
     * @param customerPolicy policy
     * @return list of payments
     */
    List<Payment> findByCustomerPolicy(CustomerPolicy customerPolicy);
}
