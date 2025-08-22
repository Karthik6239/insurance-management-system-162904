package com.example.insurancemanagementbackend.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Claim entity for claims filed against a customer's policy.
 */
@Entity
@Table(name = "claims", indexes = {
        @Index(name = "ix_claims_reference", columnList = "reference", unique = true)
})
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Human-readable claim reference
    @Column(nullable = false, unique = true, length = 64)
    private String reference;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_policy_id", nullable = false, foreignKey = @ForeignKey(name = "fk_claim_customer_policy"))
    private CustomerPolicy customerPolicy;

    // Who created (agent or customer)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false, foreignKey = @ForeignKey(name = "fk_claim_created_by_user"))
    private User createdBy;

    // Customer the claim belongs to (redundant to customerPolicy.customer but helps queries)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_claim_customer_user"))
    private User customer;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal claimAmount;

    @Column(length = 512)
    private String description;

    // Current status materialized for quick access
    @Column(length = 32, nullable = false)
    private String status = "SUBMITTED";

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    @OneToMany(mappedBy = "claim", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClaimStatusHistory> statusHistory = new HashSet<>();

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    // Getters/setters
    public Long getId() { return id; }
    public Claim setId(Long id) { this.id = id; return this; }

    public String getReference() { return reference; }
    public Claim setReference(String reference) { this.reference = reference; return this; }

    public CustomerPolicy getCustomerPolicy() { return customerPolicy; }
    public Claim setCustomerPolicy(CustomerPolicy customerPolicy) { this.customerPolicy = customerPolicy; return this; }

    public User getCreatedBy() { return createdBy; }
    public Claim setCreatedBy(User createdBy) { this.createdBy = createdBy; return this; }

    public User getCustomer() { return customer; }
    public Claim setCustomer(User customer) { this.customer = customer; return this; }

    public BigDecimal getClaimAmount() { return claimAmount; }
    public Claim setClaimAmount(BigDecimal claimAmount) { this.claimAmount = claimAmount; return this; }

    public String getDescription() { return description; }
    public Claim setDescription(String description) { this.description = description; return this; }

    public String getStatus() { return status; }
    public Claim setStatus(String status) { this.status = status; return this; }

    public Instant getCreatedAt() { return createdAt; }
    public Claim setCreatedAt(Instant createdAt) { this.createdAt = createdAt; return this; }

    public Instant getUpdatedAt() { return updatedAt; }
    public Claim setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }

    public Set<ClaimStatusHistory> getStatusHistory() { return statusHistory; }
    public Claim setStatusHistory(Set<ClaimStatusHistory> statusHistory) { this.statusHistory = statusHistory; return this; }
}
