package com.meenz.aistockdashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final StockService stockService;

    public DashboardController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        PortfolioSummary summary =
                stockService.getPortfolioSummary();

        model.addAttribute("summary", summary);

        return "dashboard";
    }
}