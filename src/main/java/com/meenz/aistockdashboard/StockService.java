package com.meenz.aistockdashboard;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

@Service
public class StockService {

    private final RestClient restClient;

    public StockService() {
        this.restClient = RestClient.create();
    }

    @Value("${finnhub.api.key}")
    private String apiKey;
    
    public Stock getStock(String symbol) {
       
        String url =
                "https://finnhub.io/api/v1/quote?symbol="
                        + symbol
                        + "&token="
                        + apiKey;

        FinnhubResponse response =
                restClient.get()
                        .uri(url)
                        .retrieve()
                        .body(FinnhubResponse.class);

        return new Stock(symbol, response.getC());
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

        List<PortfolioPosition> positions = new ArrayList<>();

        Stock avgo = getStock("AVGO");
        positions.add(
                new PortfolioPosition(
                        "AVGO",
                        avgo.getPrice(),
                        5,
                        300
                )
        );

        Stock nvda = getStock("NVDA");
        positions.add(
                new PortfolioPosition(
                        "NVDA",
                        nvda.getPrice(),
                        10,
                        150
                )
        );  

        Stock tsla = getStock("TSLA");
        positions.add(
                new PortfolioPosition(
                        "TSLA",
                        tsla.getPrice(),
                        3,
                        250
                )
        );

        double totalValue = 0;

        for (PortfolioPosition p : positions) {
            totalValue += p.getMarketValue();
        }

        for (PortfolioPosition p : positions) {

            double allocation =
                Math.round(
                    (p.getMarketValue() / totalValue) * 100 * 100
                ) / 100.0;
            p.setAllocation(allocation);
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
}