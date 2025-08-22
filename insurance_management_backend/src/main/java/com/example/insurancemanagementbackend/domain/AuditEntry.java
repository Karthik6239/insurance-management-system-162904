package com.example.insurancemanagementbackend.domain;

import jakarta.persistence.*;

import java.time.Instant;

/**
 * AuditEntry records important user actions and system events.
 */
@Entity
@Table(name = "audit_entries", indexes = {
        @Index(name = "ix_audit_entity_type_id", columnList = "entityType, entityId")
})
public class AuditEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Actor who performed the action
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id", foreignKey = @ForeignKey(name = "fk_audit_actor_user"))
    private User actor;

    // Basic details
    @Column(length = 64, nullable = false)
    private String action;

    @Column(length = 64)
    private String entityType;

    @Column
    private Long entityId;

    @Column(length = 512)
    private String details;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    // Getters/setters
    public Long getId() { return id; }
    public AuditEntry setId(Long id) { this.id = id; return this; }

    public User getActor() { return actor; }
    public AuditEntry setActor(User actor) { this.actor = actor; return this; }

    public String getAction() { return action; }
    public AuditEntry setAction(String action) { this.action = action; return this; }

    public String getEntityType() { return entityType; }
    public AuditEntry setEntityType(String entityType) { this.entityType = entityType; return this; }

    public Long getEntityId() { return entityId; }
    public AuditEntry setEntityId(Long entityId) { this.entityId = entityId; return this; }

    public String getDetails() { return details; }
    public AuditEntry setDetails(String details) { this.details = details; return this; }

    public Instant getCreatedAt() { return createdAt; }
    public AuditEntry setCreatedAt(Instant createdAt) { this.createdAt = createdAt; return this; }
}
