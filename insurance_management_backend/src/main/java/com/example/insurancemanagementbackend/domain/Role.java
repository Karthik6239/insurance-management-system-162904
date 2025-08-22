package com.example.insurancemanagementbackend.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Role entity representing an application role (e.g., ADMIN, AGENT, CUSTOMER).
 */
@Entity
@Table(name = "roles", uniqueConstraints = {
        @UniqueConstraint(name = "uk_roles_name", columnNames = {"name"})
})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64, unique = true)
    private String name;

    @Column(length = 256)
    private String description;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    // Getters and setters
    public Long getId() { return id; }
    public Role setId(Long id) { this.id = id; return this; }

    public String getName() { return name; }
    public Role setName(String name) { this.name = name; return this; }

    public String getDescription() { return description; }
    public Role setDescription(String description) { this.description = description; return this; }

    public Instant getCreatedAt() { return createdAt; }
    public Role setCreatedAt(Instant createdAt) { this.createdAt = createdAt; return this; }

    public Set<User> getUsers() { return users; }
    public Role setUsers(Set<User> users) { this.users = users; return this; }
}
