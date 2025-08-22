package com.example.insurancemanagementbackend.domain;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * ClaimStatusHistory captures each status change for a claim, including actor and notes.
 */
@Entity
@Table(name = "claim_status_history")
public class ClaimStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Associations
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "claim_id", nullable = false, foreignKey = @ForeignKey(name = "fk_status_history_claim"))
    private Claim claim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id", foreignKey = @ForeignKey(name = "fk_status_history_actor_user"))
    private User actor;

    // Old/new statuses and notes
    @Column(name = "from_status", length = 32)
    private String fromStatus;

    @Column(name = "to_status", length = 32, nullable = false)
    private String toStatus;

    @Column(length = 512)
    private String notes;

    @Column(nullable = false, updatable = false)
    private Instant changedAt = Instant.now();

    // Getters/setters
    public Long getId() { return id; }
    public ClaimStatusHistory setId(Long id) { this.id = id; return this; }

    public Claim getClaim() { return claim; }
    public ClaimStatusHistory setClaim(Claim claim) { this.claim = claim; return this; }

    public User getActor() { return actor; }
    public ClaimStatusHistory setActor(User actor) { this.actor = actor; return this; }

    public String getFromStatus() { return fromStatus; }
    public ClaimStatusHistory setFromStatus(String fromStatus) { this.fromStatus = fromStatus; return this; }

    public String getToStatus() { return toStatus; }
    public ClaimStatusHistory setToStatus(String toStatus) { this.toStatus = toStatus; return this; }

    public String getNotes() { return notes; }
    public ClaimStatusHistory setNotes(String notes) { this.notes = notes; return this; }

    public Instant getChangedAt() { return changedAt; }
    public ClaimStatusHistory setChangedAt(Instant changedAt) { this.changedAt = changedAt; return this; }
}
