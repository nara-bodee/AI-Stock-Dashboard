package com.meenz.aistockdashboard;

public class PortfolioPosition {

    private String symbol;
    private double price;
    private int shares;
    private double avgCost;
    private double marketValue;
    private double costBasis;
    private double gainLoss;
    private double allocation;

    public PortfolioPosition(
            String symbol,
            double price,
            int shares,
            double avgCost
    ) {
        this.symbol = symbol;
        this.price = price;
        this.shares = shares;
        this.avgCost = avgCost;

        this.marketValue = price * shares;
        this.costBasis = avgCost * shares;
        this.gainLoss = marketValue - costBasis;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public int getShares() {
        return shares;
    }

    public double getAvgCost() {
        return avgCost;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public double getCostBasis() {
        return costBasis;
    }

    public double getGainLoss() {
        return gainLoss;
    }

    public double getAllocation() {
        return allocation;
    }

    public void setAllocation(double allocation) {
        this.allocation = allocation;
    }

    public void setPrice(double price) {
    this.price = price;

    this.marketValue =
            price * shares;

    this.gainLoss =
            marketValue - costBasis;
    }
}