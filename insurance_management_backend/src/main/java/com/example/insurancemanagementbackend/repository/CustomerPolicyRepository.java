package com.example.insurancemanagementbackend.repository;

import com.example.insurancemanagementbackend.domain.CustomerPolicy;
import com.example.insurancemanagementbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for CustomerPolicy entity.
 */
public interface CustomerPolicyRepository extends JpaRepository<CustomerPolicy, Long> {

    // PUBLIC_INTERFACE
    /**
     * Find all customer policies for a given user.
     * @param customer user
     * @return list of policies
     */
    List<CustomerPolicy> findByCustomer(User customer);

    // PUBLIC_INTERFACE
    /**
     * Find active policies for a user.
     * @param customer user
     * @param active whether active
     * @return list of customer policies
     */
    List<CustomerPolicy> findByCustomerAndActive(User customer, boolean active);

    // PUBLIC_INTERFACE
    /**
     * Find active policies overlapping a date.
     * @param date date to check
     * @return list of active policies
     */
    List<CustomerPolicy> findByStartDateBeforeAndEndDateAfter(LocalDate date1, LocalDate date2);
}
