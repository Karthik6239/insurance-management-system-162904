package com.example.insurancemanagementbackend.service.impl;

import com.example.insurancemanagementbackend.domain.User;
import com.example.insurancemanagementbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Bridges our User entity to Spring Security's UserDetailsService.
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    @Lazy
    private UserRepository userRepository;

    // PUBLIC_INTERFACE
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (u.isLocked()) {
            throw new LockedException("User is locked");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getEmail())
                .password(u.getPasswordHash())
                .accountLocked(u.isLocked())
                .disabled(!u.isEnabled())
                .authorities(u.getRoles().stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName().toUpperCase()))
                        .collect(Collectors.toSet()))
                .build();
    }
}
