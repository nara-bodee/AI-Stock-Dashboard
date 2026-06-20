package com.meenz.aistockdashboard.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.ArrayList;

import com.meenz.aistockdashboard.dto.BuyRequest;
import com.meenz.aistockdashboard.dto.PerformanceSummary;
import com.meenz.aistockdashboard.dto.PortfolioSummary;
import com.meenz.aistockdashboard.dto.SellRequest;
import com.meenz.aistockdashboard.dto.TransactionRecord;
import com.meenz.aistockdashboard.model.PortfolioPosition;
import com.meenz.aistockdashboard.model.Stock;
import com.meenz.aistockdashboard.repository.PortfolioRepository;

@Service
public class StockService {

    private final PortfolioRepository portfolioRepository;

    public StockService(
        PortfolioRepository portfolioRepository
    ) {
        this.portfolioRepository = portfolioRepository;
    }

    @Value("${finnhub.api.key}")
    private String apiKey;
    
    public Stock getStock(String symbol) {

        try {
            String url =
                    "https://finnhub.io/api/v1/quote?symbol="
                            + URLEncoder.encode(symbol, StandardCharsets.UTF_8)
                            + "&token="
                            + URLEncoder.encode(apiKey, StandardCharsets.UTF_8);
    
            ProcessBuilder builder = new ProcessBuilder(
                    "powershell",
                    "-Command",
                    "(Invoke-RestMethod '" + url + "') | ConvertTo-Json"
            );
    
            Process process = builder.start();
    
            String json =
                    new String(
                            process.getInputStream().readAllBytes(),
                            StandardCharsets.UTF_8
                    );
    
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
    
            double price = root.get("c").asDouble();
    
            return new Stock(symbol, price);
    
        } catch (Exception e) {
            System.out.println("Finnhub API failed for " + symbol + ": " + e.getMessage());
            return new Stock(symbol, 0);
        }
    }

    public List<Stock> getPortfolio() {

        List<String> symbols = List.of(
                "AVGO",
                "NVDA",
                "TSLA",
                "MU",
                "PLTR"
        );

        List<Stock> portfolio = new ArrayList<>();

        for (String symbol : symbols) {
            portfolio.add(getStock(symbol));
        }

        return portfolio;
    }

    public List<PortfolioPosition> getPortfolioPositions() {

    List<PortfolioPosition> positions =
            portfolioRepository.getPositions();

    for (PortfolioPosition position : positions) {

        Stock stock =
                getStock(position.getSymbol());

        position.setPrice(
                stock.getPrice()
        );
    }
    
    double totalValue = 0;

    for (PortfolioPosition position : positions) {
        totalValue += position.getMarketValue();
    }

    for (PortfolioPosition position : positions) {

        double allocation =
                (position.getMarketValue()
                        / totalValue)
                        * 100;

        position.setAllocation(allocation);
    }

    return positions;
}
    
    public PortfolioSummary getPortfolioSummary() {

        List<PortfolioPosition> positions =
                getPortfolioPositions();

        double totalMarketValue = 0;
        double totalCostBasis = 0;

        for (PortfolioPosition p : positions) {

            totalMarketValue += p.getMarketValue();
            totalCostBasis += p.getCostBasis();
        }

        double totalGainLoss =
                totalMarketValue - totalCostBasis;

        return new PortfolioSummary(
                totalMarketValue,
                totalCostBasis,
                totalGainLoss,
                positions
        );
    }

    public void buyStock(BuyRequest request) {
        portfolioRepository.buyStock(request);
    }

    public void sellStock(SellRequest request) {
        portfolioRepository.sellStock(request);
    }

    public List<TransactionRecord> getTransactions() {
        return portfolioRepository.getTransactions();
    }

    public PerformanceSummary getPerformanceSummary() {

        PortfolioSummary portfolioSummary =
                getPortfolioSummary();
    
        List<TransactionRecord> transactions =
                getTransactions();
    
        double realizedGain = 0;
    
        for (TransactionRecord t : transactions) {
            realizedGain += t.getRealizedGain();
        }
    
        double unrealizedGain =
                portfolioSummary.getTotalGainLoss();
    
        double totalGain =
                realizedGain + unrealizedGain;
    
        int tradeCount =
                transactions.size();
    
        return new PerformanceSummary(
                portfolioSummary.getTotalMarketValue(),
                unrealizedGain,
                realizedGain,
                totalGain,
                tradeCount
        );
    }
}