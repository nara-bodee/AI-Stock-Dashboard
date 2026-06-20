package com.meenz.aistockdashboard.dto;

import java.util.List;

import com.meenz.aistockdashboard.model.PortfolioPosition;

public class PortfolioSummary {

    private double totalMarketValue;
    private double totalCostBasis;
    private double totalGainLoss;
    private List<PortfolioPosition> positions;

    public PortfolioSummary(
            double totalMarketValue,
            double totalCostBasis,
            double totalGainLoss,
            List<PortfolioPosition> positions) {

        this.totalMarketValue = totalMarketValue;
        this.totalCostBasis = totalCostBasis;
        this.totalGainLoss = totalGainLoss;
        this.positions = positions;
    }

    public double getTotalMarketValue() {
        return totalMarketValue;
    }

    public double getTotalCostBasis() {
        return totalCostBasis;
    }

    public double getTotalGainLoss() {
        return totalGainLoss;
    }

    public List<PortfolioPosition> getPositions() {
        return positions;
    }
}