package com.example.insurancemanagementbackend.repository;

import com.example.insurancemanagementbackend.domain.ClaimStatusHistory;
import com.example.insurancemanagementbackend.domain.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for ClaimStatusHistory entity.
 */
public interface ClaimStatusHistoryRepository extends JpaRepository<ClaimStatusHistory, Long> {

    // PUBLIC_INTERFACE
    /**
     * Find all status history records for a claim ordered by time.
     * @param claim claim
     * @return list ordered by changedAt ascending
     */
    List<ClaimStatusHistory> findByClaimOrderByChangedAtAsc(Claim claim);
}
