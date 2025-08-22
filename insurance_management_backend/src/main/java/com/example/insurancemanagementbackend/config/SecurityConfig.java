package com.example.insurancemanagementbackend.config;

import com.example.insurancemanagementbackend.security.JwtAuthenticationFilter;
import com.example.insurancemanagementbackend.security.JwtService;
import com.example.insurancemanagementbackend.service.impl.AppUserDetailsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration enabling JWT-based stateless authentication and role-based access control.
 */
@Configuration
@EnableMethodSecurity
@Tag(name = "Security", description = "Spring Security configuration with JWT and RBAC")
public class SecurityConfig {

    // PUBLIC_INTERFACE
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt for strong password hashing
        return new BCryptPasswordEncoder();
    }

    // PUBLIC_INTERFACE
    @Bean
    public UserDetailsService userDetailsService() {
        // Delegates to our custom implementation backed by UserRepository
        return new AppUserDetailsService();
    }

    // PUBLIC_INTERFACE
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService uds, PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(uds);
        provider.setPasswordEncoder(encoder);
        return new ProviderManager(provider);
    }

    // PUBLIC_INTERFACE
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtService jwtService, UserDetailsService uds) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtService, uds);

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/health", "/api/info", "/docs", "/swagger-ui.html", "/swagger-ui/**", "/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/register").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
