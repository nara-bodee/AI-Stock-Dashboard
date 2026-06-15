package com.meenz.aistockdashboard;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;

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
}