package com.meenz.aistockdashboard;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.ArrayList;

@Service
public class StockService {

    private final RestClient restClient;
    private final PortfolioRepository portfolioRepository;

    public StockService(
        PortfolioRepository portfolioRepository
    ) {
        this.restClient = RestClient.create();
        this.portfolioRepository = portfolioRepository;
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
}