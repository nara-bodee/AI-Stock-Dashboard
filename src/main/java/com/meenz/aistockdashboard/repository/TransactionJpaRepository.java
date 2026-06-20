package com.meenz.aistockdashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meenz.aistockdashboard.entity.TransactionEntity;

public interface TransactionJpaRepository
        extends JpaRepository<TransactionEntity, Long> {
}