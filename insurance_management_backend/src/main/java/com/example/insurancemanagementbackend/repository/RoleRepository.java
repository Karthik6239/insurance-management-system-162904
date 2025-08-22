package com.example.insurancemanagementbackend.repository;

import com.example.insurancemanagementbackend.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for Role entity.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    // PUBLIC_INTERFACE
    /**
     * Find role by name.
     * @param name unique role name
     * @return optional role
     */
    Optional<Role> findByName(String name);

    // PUBLIC_INTERFACE
    /**
     * Check existence by role name.
     * @param name role name
     * @return true if exists
     */
    boolean existsByName(String name);
}
