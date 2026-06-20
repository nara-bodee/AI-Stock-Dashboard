package com.meenz.aistockdashboard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String symbol;
    private int shares;
    private double price;
    private String date;
    private double realizedGain;

    public TransactionEntity() {
    }

    public TransactionEntity(
            String type,
            String symbol,
            int shares,
            double price,
            String date,
            double realizedGain
    ) {
        this.type = type;
        this.symbol = symbol;
        this.shares = shares;
        this.price = price;
        this.date = date;
        this.realizedGain = realizedGain;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getShares() {
        return shares;
    }

    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public double getRealizedGain() {
        return realizedGain;
    }
}