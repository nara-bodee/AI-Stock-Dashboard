package com.meenz.aistockdashboard;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioRecordJpaRepository
        extends JpaRepository<PortfolioRecordEntity, Long> {

    Optional<PortfolioRecordEntity> findBySymbolIgnoreCase(String symbol);
}