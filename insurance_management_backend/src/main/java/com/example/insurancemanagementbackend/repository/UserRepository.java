package com.example.insurancemanagementbackend.repository;

import com.example.insurancemanagementbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    // PUBLIC_INTERFACE
    /**
     * Find a user by email.
     * @param email unique email
     * @return optional user
     */
    Optional<User> findByEmail(String email);

    // PUBLIC_INTERFACE
    /**
     * Check if a user exists by email.
     * @param email user email
     * @return true if exists
     */
    boolean existsByEmail(String email);
}
