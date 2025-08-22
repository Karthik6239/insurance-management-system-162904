package com.example.insurancemanagementbackend.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * CustomerPolicy represents a specific policy purchased by a customer (User).
 */
@Entity
@Table(name = "customer_policies", uniqueConstraints = {
        @UniqueConstraint(name = "uk_customer_policy_unique", columnNames = {"customer_id", "policy_id", "start_date"})
})
public class CustomerPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Associations
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_customer_policy_customer"))
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "policy_id", nullable = false, foreignKey = @ForeignKey(name = "fk_customer_policy_policy"))
    private Policy policy;

    // Policy term details
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal premium;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    @OneToMany(mappedBy = "customerPolicy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Claim> claims = new HashSet<>();

    @OneToMany(mappedBy = "customerPolicy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Payment> payments = new HashSet<>();

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    // Getters/setters
    public Long getId() { return id; }
    public CustomerPolicy setId(Long id) { this.id = id; return this; }

    public User getCustomer() { return customer; }
    public CustomerPolicy setCustomer(User customer) { this.customer = customer; return this; }

    public Policy getPolicy() { return policy; }
    public CustomerPolicy setPolicy(Policy policy) { this.policy = policy; return this; }

    public LocalDate getStartDate() { return startDate; }
    public CustomerPolicy setStartDate(LocalDate startDate) { this.startDate = startDate; return this; }

    public LocalDate getEndDate() { return endDate; }
    public CustomerPolicy setEndDate(LocalDate endDate) { this.endDate = endDate; return this; }

    public BigDecimal getPremium() { return premium; }
    public CustomerPolicy setPremium(BigDecimal premium) { this.premium = premium; return this; }

    public boolean isActive() { return active; }
    public CustomerPolicy setActive(boolean active) { this.active = active; return this; }

    public Instant getCreatedAt() { return createdAt; }
    public CustomerPolicy setCreatedAt(Instant createdAt) { this.createdAt = createdAt; return this; }

    public Instant getUpdatedAt() { return updatedAt; }
    public CustomerPolicy setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }

    public Set<Claim> getClaims() { return claims; }
    public CustomerPolicy setClaims(Set<Claim> claims) { this.claims = claims; return this; }

    public Set<Payment> getPayments() { return payments; }
    public CustomerPolicy setPayments(Set<Payment> payments) { this.payments = payments; return this; }
}
