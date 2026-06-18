package com.meenz.aistockdashboard;

public class TransactionRecord {

    private String type;
    private String symbol;
    private int shares;
    private double price;
    private String date;
    private double realizedGain;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public int getShares() { return shares; }
    public void setShares(int shares) { this.shares = shares; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public double getRealizedGain() { return realizedGain; }
    public void setRealizedGain(double realizedGain) { this.realizedGain = realizedGain; }
}