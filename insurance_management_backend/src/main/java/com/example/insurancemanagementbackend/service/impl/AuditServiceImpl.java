package com.example.insurancemanagementbackend.service.impl;

import com.example.insurancemanagementbackend.domain.AuditEntry;
import com.example.insurancemanagementbackend.domain.User;
import com.example.insurancemanagementbackend.repository.AuditEntryRepository;
import com.example.insurancemanagementbackend.repository.UserRepository;
import com.example.insurancemanagementbackend.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Implementation of AuditService for creating and querying audit records.
 */
@Service
@Transactional
public class AuditServiceImpl implements AuditService {

    @Autowired private AuditEntryRepository auditRepo;
    @Autowired private UserRepository userRepository;

    // PUBLIC_INTERFACE
    @Override
    public AuditEntry audit(Long actorUserId, String action, String entityType, Long entityId, String details) {
        User actor = actorUserId != null ? userRepository.findById(actorUserId).orElse(null) : null;
        AuditEntry a = new AuditEntry()
                .setActor(actor)
                .setAction(action)
                .setEntityType(entityType)
                .setEntityId(entityId)
                .setDetails(details)
                .setCreatedAt(Instant.now());
        return auditRepo.save(a);
    }

    // PUBLIC_INTERFACE
    @Override
    @Transactional(readOnly = true)
    public List<AuditEntry> listForEntity(String entityType, Long entityId) {
        return auditRepo.findByEntityTypeAndEntityIdOrderByCreatedAtAsc(entityType, entityId);
    }
}
