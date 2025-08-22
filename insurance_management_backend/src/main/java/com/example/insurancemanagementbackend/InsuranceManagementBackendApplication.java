package com.example.insurancemanagementbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot entry point for the Insurance Management Backend application.
 * Ensures correct package structure for component scanning.
 */
// PUBLIC_INTERFACE
@SpringBootApplication
public class InsuranceManagementBackendApplication {

    /**
     * PUBLIC_INTERFACE
     * Application entry point.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(InsuranceManagementBackendApplication.class, args);
    }
}
