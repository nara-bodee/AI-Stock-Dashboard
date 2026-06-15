package com.meenz.aistockdashboard;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    private final StockService stockService;

    public HomeController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/stocks/{symbol}")
    public Stock getStock(
            @PathVariable String symbol) {

        return stockService.getStock(symbol);
    }
}