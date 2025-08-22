package com.example.insurancemanagementbackend.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Payment entity capturing premium payments or payouts related to policies/claims.
 */
@Entity
@Table(name = "payments", indexes = {
        @Index(name = "ix_payments_reference", columnList = "reference", unique = true)
})
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Payment reference
    @Column(nullable = false, unique = true, length = 64)
    private String reference;

    // Associations
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_policy_id", nullable = false, foreignKey = @ForeignKey(name = "fk_payment_customer_policy"))
    private CustomerPolicy customerPolicy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_payment_customer_user"))
    private User customer;

    // Optional association for claim payouts
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id", foreignKey = @ForeignKey(name = "fk_payment_claim"))
    private Claim claim;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(length = 32, nullable = false)
    private String method = "CARD";

    @Column(length = 32, nullable = false)
    private String direction = "IN"; // IN = premium from customer, OUT = payout to customer

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    // Getters/setters
    public Long getId() { return id; }
    public Payment setId(Long id) { this.id = id; return this; }

    public String getReference() { return reference; }
    public Payment setReference(String reference) { this.reference = reference; return this; }

    public CustomerPolicy getCustomerPolicy() { return customerPolicy; }
    public Payment setCustomerPolicy(CustomerPolicy customerPolicy) { this.customerPolicy = customerPolicy; return this; }

    public User getCustomer() { return customer; }
    public Payment setCustomer(User customer) { this.customer = customer; return this; }

    public Claim getClaim() { return claim; }
    public Payment setClaim(Claim claim) { this.claim = claim; return this; }

    public BigDecimal getAmount() { return amount; }
    public Payment setAmount(BigDecimal amount) { this.amount = amount; return this; }

    public String getMethod() { return method; }
    public Payment setMethod(String method) { this.method = method; return this; }

    public String getDirection() { return direction; }
    public Payment setDirection(String direction) { this.direction = direction; return this; }

    public Instant getCreatedAt() { return createdAt; }
    public Payment setCreatedAt(Instant createdAt) { this.createdAt = createdAt; return this; }
}
