package com.example.insurancemanagementbackend.service;

import com.example.insurancemanagementbackend.domain.Role;
import com.example.insurancemanagementbackend.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * PUBLIC_INTERFACE
 * UserService defines core contract for user management and role assignments.
 */
public interface UserService {

    /**
     * Create a new user with roles.
     * @param fullName full name
     * @param email unique email
     * @param rawPassword raw password (encoding to be handled by implementation)
     * @param roleNames role names to assign
     * @return created user
     */
    User createUser(String fullName, String email, String rawPassword, Set<String> roleNames);

    /**
     * Assign roles to user.
     * @param userId user id
     * @param roleNames role names
     * @return updated user
     */
    User assignRoles(Long userId, Set<String> roleNames);

    /**
     * Find by email.
     * @param email email
     * @return optional user
     */
    Optional<User> findByEmail(String email);

    /**
     * List all roles available in the system.
     * @return roles
     */
    List<Role> listRoles();

    /**
     * Toggle user status.
     * @param userId user id
     * @param enabled enabled flag
     * @return updated user
     */
    User setEnabled(Long userId, boolean enabled);
}
