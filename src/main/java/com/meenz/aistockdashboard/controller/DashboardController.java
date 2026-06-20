package com.meenz.aistockdashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.meenz.aistockdashboard.service.StockService;

@Controller
public class DashboardController {

    private final StockService stockService;

    public DashboardController(
            StockService stockService
    ) {
        this.stockService = stockService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute(
                "positions",
                stockService.getPortfolioPositions()
        );

        return "dashboard";
    }

    @GetMapping("/dashboard/transactions")
    public String transactions(Model model) {

        model.addAttribute(
                "transactions",
                stockService.getTransactions()
        );

        return "transactions";
    }
}