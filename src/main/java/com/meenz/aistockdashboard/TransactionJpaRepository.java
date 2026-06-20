package com.meenz.aistockdashboard;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionJpaRepository
        extends JpaRepository<TransactionEntity, Long> {
}