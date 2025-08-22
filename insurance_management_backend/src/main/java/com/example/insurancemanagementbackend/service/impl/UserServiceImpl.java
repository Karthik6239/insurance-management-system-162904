package com.example.insurancemanagementbackend.service.impl;

import com.example.insurancemanagementbackend.domain.Role;
import com.example.insurancemanagementbackend.domain.User;
import com.example.insurancemanagementbackend.repository.RoleRepository;
import com.example.insurancemanagementbackend.repository.UserRepository;
import com.example.insurancemanagementbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of UserService managing users and role assignments.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    // PUBLIC_INTERFACE
    @Override
    public User createUser(String fullName, String email, String rawPassword, Set<String> roleNames) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already in use");
        }
        Set<Role> roles = resolveRoles(roleNames);
        User user = new User()
                .setFullName(fullName)
                .setEmail(email)
                .setPasswordHash(passwordEncoder.encode(rawPassword))
                .setEnabled(true)
                .setLocked(false)
                .setRoles(roles);
        return userRepository.save(user);
    }

    // PUBLIC_INTERFACE
    @Override
    public User assignRoles(Long userId, Set<String> roleNames) {
        User user = userRepository.findById(userId).orElseThrow();
        Set<Role> roles = resolveRoles(roleNames);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    // PUBLIC_INTERFACE
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // PUBLIC_INTERFACE
    @Override
    @Transactional(readOnly = true)
    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    // PUBLIC_INTERFACE
    @Override
    public User setEnabled(Long userId, boolean enabled) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setEnabled(enabled);
        return userRepository.save(user);
    }

    private Set<Role> resolveRoles(Set<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
            // default to CUSTOMER if nothing provided
            Role defaultRole = roleRepository.findByName("CUSTOMER")
                    .orElseGet(() -> roleRepository.save(new Role("CUSTOMER")));
            return Set.of(defaultRole);
        }
        Set<String> upper = roleNames.stream().filter(Objects::nonNull).map(String::toUpperCase).collect(Collectors.toSet());
        Set<Role> roles = new HashSet<>();
        for (String rn : upper) {
            Role r = roleRepository.findByName(rn).orElseGet(() -> roleRepository.save(new Role(rn)));
            roles.add(r);
        }
        return roles;
    }
}
