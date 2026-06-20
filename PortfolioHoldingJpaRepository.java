package com.meenz.aistockdashboard;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioHoldingJpaRepository
        extends JpaRepository<PortfolioHoldingEntity, Long> {

    Optional<PortfolioHoldingEntity> findBySymbolIgnoreCase(String symbol);
}