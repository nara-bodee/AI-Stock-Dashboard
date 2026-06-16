package com.meenz.aistockdashboard;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PortfolioRepository {

    public List<PortfolioPosition> getPositions() {

        return List.of(
                new PortfolioPosition(
                        "AVGO",
                        0,
                        5,
                        300
                ),
                new PortfolioPosition(
                        "NVDA",
                        0,
                        10,
                        150
                ),
                new PortfolioPosition(
                        "TSLA",
                        0,
                        3,
                        250
                )
        );
    }
}