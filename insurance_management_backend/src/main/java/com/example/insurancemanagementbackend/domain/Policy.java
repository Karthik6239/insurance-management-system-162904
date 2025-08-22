package com.example.insurancemanagementbackend.domain;

import com.example.insurancemanagementbackend.domain.enums.PolicyType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Policy entity represents a policy product offered by the company.
 */
@Entity
@Table(name = "policies", indexes = {
        @Index(name = "ix_policies_code", columnList = "code", unique = true)
})
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Business identifiers and details
    @Column(nullable = false, length = 64, unique = true)
    private String code;

    @Column(nullable = false, length = 128)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private PolicyType type;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal basePremium;

    @Column(precision = 15, scale = 2)
    private BigDecimal coverageAmount;

    @Column(length = 512)
    private String description;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    @OneToMany(mappedBy = "policy", fetch = FetchType.LAZY)
    private Set<CustomerPolicy> customerPolicies = new HashSet<>();

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    // Getters/setters
    public Long getId() { return id; }
    public Policy setId(Long id) { this.id = id; return this; }

    public String getCode() { return code; }
    public Policy setCode(String code) { this.code = code; return this; }

    public String getName() { return name; }
    public Policy setName(String name) { this.name = name; return this; }

    public PolicyType getType() { return type; }
    public Policy setType(PolicyType type) { this.type = type; return this; }

    public BigDecimal getBasePremium() { return basePremium; }
    public Policy setBasePremium(BigDecimal basePremium) { this.basePremium = basePremium; return this; }

    public BigDecimal getCoverageAmount() { return coverageAmount; }
    public Policy setCoverageAmount(BigDecimal coverageAmount) { this.coverageAmount = coverageAmount; return this; }

    public String getDescription() { return description; }
    public Policy setDescription(String description) { this.description = description; return this; }

    public Instant getCreatedAt() { return createdAt; }
    public Policy setCreatedAt(Instant createdAt) { this.createdAt = createdAt; return this; }

    public Instant getUpdatedAt() { return updatedAt; }
    public Policy setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }

    public Set<CustomerPolicy> getCustomerPolicies() { return customerPolicies; }
    public Policy setCustomerPolicies(Set<CustomerPolicy> customerPolicies) { this.customerPolicies = customerPolicies; return this; }
}
