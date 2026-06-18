package com.meenz.aistockdashboard;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class PortfolioRepository {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String filePath = "src/main/resources/portfolio.json";

    public List<PortfolioPosition> getPositions() {

        try {
            InputStream inputStream =
                    new ClassPathResource("portfolio.json")
                            .getInputStream();

            List<PortfolioRecord> records =
                    mapper.readValue(
                            inputStream,
                            new TypeReference<List<PortfolioRecord>>() {}
                    );

            return records.stream()
                    .map(r ->
                            new PortfolioPosition(
                                    r.getSymbol(),
                                    0,
                                    r.getShares(),
                                    r.getAvgCost()
                            ))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void buyStock(BuyRequest request) {

        try {
            File file = new File(filePath);

            List<PortfolioRecord> records =
                    mapper.readValue(
                            file,
                            new TypeReference<List<PortfolioRecord>>() {}
                    );

            boolean found = false;

            for (PortfolioRecord record : records) {

                if (record.getSymbol().equalsIgnoreCase(request.getSymbol())) {

                    int oldShares = record.getShares();
                    double oldAvgCost = record.getAvgCost();

                    int newShares = oldShares + request.getShares();

                    double newAvgCost =
                            ((oldShares * oldAvgCost)
                                    + (request.getShares() * request.getPrice()))
                                    / newShares;

                    record.setShares(newShares);
                    record.setAvgCost(newAvgCost);
                    record.setSymbol(record.getSymbol().toUpperCase());

                    found = true;
                    break;
                }
            }

            if (!found) {
                PortfolioRecord newRecord = new PortfolioRecord();

                newRecord.setSymbol(request.getSymbol().toUpperCase());
                newRecord.setShares(request.getShares());
                newRecord.setAvgCost(request.getPrice());

                records.add(newRecord);
            }

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, records);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TransactionRecord transaction = new TransactionRecord();

        transaction.setType("BUY");
        transaction.setSymbol(request.getSymbol().toUpperCase());
        transaction.setShares(request.getShares());
        transaction.setPrice(request.getPrice());
        transaction.setRealizedGain(0);

        saveTransaction(transaction);
    }

    public void saveTransaction(TransactionRecord transaction) {

        try {
            File file = new File("src/main/resources/transactions.json");
    
            List<TransactionRecord> transactions;
    
            if (file.length() == 0) {
                transactions = new ArrayList<>();
            } else {
                transactions = mapper.readValue(
                        file,
                        new TypeReference<List<TransactionRecord>>() {}
                );
            }
    
            transaction.setDate(LocalDate.now().toString());
    
            transactions.add(transaction);
    
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, transactions);
    
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sellStock(SellRequest request) {

        try {
            File file = new File(filePath);
    
            List<PortfolioRecord> records =
                    mapper.readValue(
                            file,
                            new TypeReference<List<PortfolioRecord>>() {}
                    );
    
            boolean found = false;
    
            for (PortfolioRecord record : records) {
    
                if (record.getSymbol().equalsIgnoreCase(request.getSymbol())) {
    
                    found = true;
    
                    if (request.getShares() > record.getShares()) {
                        throw new RuntimeException("Not enough shares to sell");
                    }
    
                    int newShares =
                            record.getShares() - request.getShares();
    
                    double realizedGain =
                            (request.getPrice() - record.getAvgCost())
                                    * request.getShares();
    
                    if (newShares == 0) {
                        records.remove(record);
                    } else {
                        record.setShares(newShares);
                    }
    
                    TransactionRecord transaction =
                            new TransactionRecord();
    
                    transaction.setType("SELL");
                    transaction.setSymbol(request.getSymbol().toUpperCase());
                    transaction.setShares(request.getShares());
                    transaction.setPrice(request.getPrice());
                    transaction.setRealizedGain(realizedGain);
    
                    saveTransaction(transaction);
    
                    break;
                }
            }
    
            if (!found) {
                throw new RuntimeException("Stock not found in portfolio");
            }
    
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, records);
    
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<TransactionRecord> getTransactions() {

        try {
            File file = new File("src/main/resources/transactions.json");
    
            return mapper.readValue(
                    file,
                    new TypeReference<List<TransactionRecord>>() {}
            );
    
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}