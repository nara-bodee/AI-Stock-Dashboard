package com.meenz.aistockdashboard;

import org.springframework.stereotype.Service;

@Service
public class StockService {

    public Stock getStock(String symbol) {

        if (symbol.equalsIgnoreCase("AVGO")) {
            return new Stock("AVGO", 310.50);
        }

        if (symbol.equalsIgnoreCase("NVDA")) {
            return new Stock("NVDA", 180.25);
        }

        if (symbol.equalsIgnoreCase("TSLA")) {
            return new Stock("TSLA", 340.10);
        }

        return new Stock(symbol, 0);
    }
}