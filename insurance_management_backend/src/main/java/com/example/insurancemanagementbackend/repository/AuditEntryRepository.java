package com.example.insurancemanagementbackend.repository;

import com.example.insurancemanagementbackend.domain.AuditEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for AuditEntry entity.
 */
public interface AuditEntryRepository extends JpaRepository<AuditEntry, Long> {

    // PUBLIC_INTERFACE
    /**
     * Find audit entries for a target entity.
     * @param entityType type, e.g. 'Claim'
     * @param entityId id of target entity
     * @return list of audit entries ordered by createdAt asc
     */
    List<AuditEntry> findByEntityTypeAndEntityIdOrderByCreatedAtAsc(String entityType, Long entityId);
}
