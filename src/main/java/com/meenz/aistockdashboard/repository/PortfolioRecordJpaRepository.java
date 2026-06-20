package com.meenz.aistockdashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meenz.aistockdashboard.entity.PortfolioRecordEntity;

import java.util.Optional;

public interface PortfolioRecordJpaRepository
        extends JpaRepository<PortfolioRecordEntity, Long> {

    Optional<PortfolioRecordEntity> findBySymbolIgnoreCase(String symbol);
}