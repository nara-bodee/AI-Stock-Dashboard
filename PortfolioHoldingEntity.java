package com.meenz.aistockdashboard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "portfolio_holdings")
public class PortfolioHoldingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private int shares;
    private double avgCost;

    public PortfolioHoldingEntity() {
    }

    public PortfolioHoldingEntity(String symbol, int shares, double avgCost) {
        this.symbol = symbol;
        this.shares = shares;
        this.avgCost = avgCost;
    }

    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public double getAvgCost() {
        return avgCost;
    }

    public void setAvgCost(double avgCost) {
        this.avgCost = avgCost;
    }
}