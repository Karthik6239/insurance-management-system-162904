package com.example.insurancemanagementbackend.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity with role assignments. Users can be admins, agents, or customers.
 */
@Entity
@Table(name = "users", indexes = {
        @Index(name = "ix_users_email", columnList = "email", unique = true)
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic identity
    @Column(nullable = false, length = 128)
    private String fullName;

    @Column(nullable = false, length = 128, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    // Optional phone contact
    @Column(length = 32)
    private String phone;

    // Account flags
    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private boolean locked = false;

    // Auditing timestamps
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    // Role relationship
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_roles_user")),
            inverseJoinColumns = @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_user_roles_role")),
            uniqueConstraints = @UniqueConstraint(name = "uk_user_roles_user_role", columnNames = {"user_id", "role_id"})
    )
    private Set<Role> roles = new HashSet<>();

    // Reverse relations for domain activities
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CustomerPolicy> customerPolicies = new HashSet<>();

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<Claim> claimsCreated = new HashSet<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Claim> claimsAsCustomer = new HashSet<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "actor", fetch = FetchType.LAZY)
    private Set<AuditEntry> auditEntries = new HashSet<>();

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    // Getters/setters
    public Long getId() { return id; }
    public User setId(Long id) { this.id = id; return this; }

    public String getFullName() { return fullName; }
    public User setFullName(String fullName) { this.fullName = fullName; return this; }

    public String getEmail() { return email; }
    public User setEmail(String email) { this.email = email; return this; }

    public String getPasswordHash() { return passwordHash; }
    public User setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; return this; }

    public String getPhone() { return phone; }
    public User setPhone(String phone) { this.phone = phone; return this; }

    public boolean isEnabled() { return enabled; }
    public User setEnabled(boolean enabled) { this.enabled = enabled; return this; }

    public boolean isLocked() { return locked; }
    public User setLocked(boolean locked) { this.locked = locked; return this; }

    public Instant getCreatedAt() { return createdAt; }
    public User setCreatedAt(Instant createdAt) { this.createdAt = createdAt; return this; }

    public Instant getUpdatedAt() { return updatedAt; }
    public User setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }

    public Set<Role> getRoles() { return roles; }
    public User setRoles(Set<Role> roles) { this.roles = roles; return this; }

    public Set<CustomerPolicy> getCustomerPolicies() { return customerPolicies; }
    public User setCustomerPolicies(Set<CustomerPolicy> customerPolicies) { this.customerPolicies = customerPolicies; return this; }

    public Set<Claim> getClaimsCreated() { return claimsCreated; }
    public User setClaimsCreated(Set<Claim> claimsCreated) { this.claimsCreated = claimsCreated; return this; }

    public Set<Claim> getClaimsAsCustomer() { return claimsAsCustomer; }
    public User setClaimsAsCustomer(Set<Claim> claimsAsCustomer) { this.claimsAsCustomer = claimsAsCustomer; return this; }

    public Set<Payment> getPayments() { return payments; }
    public User setPayments(Set<Payment> payments) { this.payments = payments; return this; }

    public Set<AuditEntry> getAuditEntries() { return auditEntries; }
    public User setAuditEntries(Set<AuditEntry> auditEntries) { this.auditEntries = auditEntries; return this; }
}
