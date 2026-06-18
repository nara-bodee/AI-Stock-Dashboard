package com.meenz.aistockdashboard;

public class PerformanceSummary {

    private double totalPortfolioValue;
    private double unrealizedGain;
    private double realizedGain;
    private int tradeCount;

    public PerformanceSummary(
            double totalPortfolioValue,
            double unrealizedGain,
            double realizedGain,
            int tradeCount
    ) {
        this.totalPortfolioValue = totalPortfolioValue;
        this.unrealizedGain = unrealizedGain;
        this.realizedGain = realizedGain;
        this.tradeCount = tradeCount;
    }

    public double getTotalPortfolioValue() {
        return totalPortfolioValue;
    }

    public double getUnrealizedGain() {
        return unrealizedGain;
    }

    public double getRealizedGain() {
        return realizedGain;
    }

    public int getTradeCount() {
        return tradeCount;
    }
}