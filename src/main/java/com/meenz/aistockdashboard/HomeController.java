package com.meenz.aistockdashboard;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public List<PortfolioPosition> buyStock(
            @RequestBody BuyRequest request) {

        stockService.buyStock(request);

        return stockService.getPortfolioPositions();
    }
}