package com.meenz.aistockdashboard;

import java.util.List;

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