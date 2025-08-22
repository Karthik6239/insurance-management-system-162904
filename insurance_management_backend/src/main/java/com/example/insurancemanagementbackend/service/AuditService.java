package com.example.insurancemanagementbackend.service;

import com.example.insurancemanagementbackend.domain.AuditEntry;

import java.util.List;

/**
 * PUBLIC_INTERFACE
 * AuditService defines operations to create and query audit entries.
 */
public interface AuditService {

    /**
     * Create an audit record.
     * @param actorUserId actor
     * @param action action verb
     * @param entityType target type
     * @param entityId target id
     * @param details details
     * @return created audit entry
     */
    AuditEntry audit(Long actorUserId, String action, String entityType, Long entityId, String details);

    /**
     * List audit records for an entity.
     * @param entityType type
     * @param entityId id
     * @return list
     */
    List<AuditEntry> listForEntity(String entityType, Long entityId);
}
