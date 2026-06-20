package com.meenz.aistockdashboard.dto;

import com.meenz.aistockdashboard.util.NumberUtil;

public class PerformanceSummary {

    private double totalPortfolioValue;
    private double unrealizedGain;
    private double realizedGain;
    private double totalGain;
    private int tradeCount;

    public PerformanceSummary(
            double totalPortfolioValue,
            double unrealizedGain,
            double realizedGain,
            double totalGain,
            int tradeCount
    ) {
        this.totalPortfolioValue = totalPortfolioValue;
        this.unrealizedGain = unrealizedGain;
        this.realizedGain = realizedGain;
        this.totalGain = totalGain;
        this.tradeCount = tradeCount;
    }

    public double getTotalPortfolioValue() {
        return NumberUtil.round2(totalPortfolioValue);
    }
    
    public double getUnrealizedGain() {
        return NumberUtil.round2(unrealizedGain);
    }
    
    public double getRealizedGain() {
        return NumberUtil.round2(realizedGain);
    }
    
    public double getTotalGain() {
        return NumberUtil.round2(totalGain);
    }
    
    public int getTradeCount() {
        return tradeCount;
    }
}