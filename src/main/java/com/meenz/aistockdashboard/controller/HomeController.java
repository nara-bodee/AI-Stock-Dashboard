package com.meenz.aistockdashboard.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.meenz.aistockdashboard.dto.BuyRequest;
import com.meenz.aistockdashboard.dto.PerformanceSummary;
import com.meenz.aistockdashboard.dto.PortfolioSummary;
import com.meenz.aistockdashboard.dto.SellRequest;
import com.meenz.aistockdashboard.dto.TransactionRecord;
import com.meenz.aistockdashboard.model.PortfolioPosition;
import com.meenz.aistockdashboard.model.Stock;
import com.meenz.aistockdashboard.service.StockService;

@RestController
public class HomeController {

    private final StockService stockService;

    public HomeController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/stocks/{symbol}")
    public Stock getStock(@PathVariable String symbol) {
        return stockService.getStock(symbol);
    }

    @GetMapping("/portfolio")
    public List<Stock> getPortfolio() {
        return stockService.getPortfolio();
    }

    @GetMapping("/portfolio/details")
    public List<PortfolioPosition> getPortfolioDetails() {
        return stockService.getPortfolioPositions();
    }

    @GetMapping("/portfolio/summary")
    public PortfolioSummary getPortfolioSummary() {
        return stockService.getPortfolioSummary();
    }
    
    @PostMapping("/portfolio/buy")
    public String buyStock(
            @RequestBody BuyRequest request) {

        stockService.buyStock(request);

        return "Buy stock success";
    }

    @PostMapping("/portfolio/sell")
    public String sellStock(
            @RequestBody SellRequest request) {

        stockService.sellStock(request);

        return "Sell stock success";
    }

    @GetMapping("/transactions")
    public List<TransactionRecord> getTransactions() {
        return stockService.getTransactions();
    }

    @GetMapping("/performance")
    public PerformanceSummary getPerformance() {
        return stockService.getPerformanceSummary();
    }
}