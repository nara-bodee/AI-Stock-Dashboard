package com.meenz.aistockdashboard.repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.meenz.aistockdashboard.dto.BuyRequest;
import com.meenz.aistockdashboard.dto.SellRequest;
import com.meenz.aistockdashboard.dto.TransactionRecord;
import com.meenz.aistockdashboard.entity.PortfolioRecordEntity;
import com.meenz.aistockdashboard.entity.TransactionEntity;
import com.meenz.aistockdashboard.model.PortfolioPosition;

@Repository
public class PortfolioRepository {

    private final PortfolioRecordJpaRepository portfolioRecordRepository;
    private final TransactionJpaRepository transactionRepository;

    public PortfolioRepository(
            PortfolioRecordJpaRepository portfolioRecordRepository,
            TransactionJpaRepository transactionRepository
    ) {
        this.portfolioRecordRepository = portfolioRecordRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<PortfolioPosition> getPositions() {

        List<PortfolioRecordEntity> records =
                portfolioRecordRepository.findAll();

        return records.stream()
                .map(record ->
                        new PortfolioPosition(
                                record.getSymbol(),
                                0,
                                record.getShares(),
                                record.getAvgCost()
                        )
                )
                .collect(Collectors.toList());
    }

    public void buyStock(BuyRequest request) {

        String symbol =
                request.getSymbol().toUpperCase();

        PortfolioRecordEntity record =
                portfolioRecordRepository
                        .findBySymbolIgnoreCase(symbol)
                        .orElse(null);

        if (record == null) {

            PortfolioRecordEntity newRecord =
                    new PortfolioRecordEntity(
                            symbol,
                            request.getShares(),
                            request.getPrice()
                    );

            portfolioRecordRepository.save(newRecord);

        } else {

            int oldShares =
                    record.getShares();

            double oldAvgCost =
                    record.getAvgCost();

            int newShares =
                    oldShares + request.getShares();

            double newAvgCost =
                    ((oldShares * oldAvgCost)
                            + (request.getShares() * request.getPrice()))
                            / newShares;

            record.setShares(newShares);
            record.setAvgCost(newAvgCost);

            portfolioRecordRepository.save(record);
        }

        TransactionEntity transaction =
                new TransactionEntity(
                        "BUY",
                        symbol,
                        request.getShares(),
                        request.getPrice(),
                        LocalDate.now().toString(),
                        0
                );

        transactionRepository.save(transaction);
    }

    public void sellStock(SellRequest request) {

        String symbol =
                request.getSymbol().toUpperCase();

        PortfolioRecordEntity record =
                portfolioRecordRepository
                        .findBySymbolIgnoreCase(symbol)
                        .orElseThrow(() ->
                                new RuntimeException("Stock not found in portfolio")
                        );

        if (request.getShares() > record.getShares()) {
            throw new RuntimeException("Not enough shares to sell");
        }

        int newShares =
                record.getShares() - request.getShares();

        double realizedGain =
                (request.getPrice() - record.getAvgCost())
                        * request.getShares();

        if (newShares == 0) {
            portfolioRecordRepository.delete(record);
        } else {
            record.setShares(newShares);
            portfolioRecordRepository.save(record);
        }

        TransactionEntity transaction =
                new TransactionEntity(
                        "SELL",
                        symbol,
                        request.getShares(),
                        request.getPrice(),
                        LocalDate.now().toString(),
                        realizedGain
                );

        transactionRepository.save(transaction);
    }

    public List<TransactionRecord> getTransactions() {

        List<TransactionEntity> transactions =
                transactionRepository.findAll();

        return transactions.stream()
                .map(entity -> {
                    TransactionRecord record =
                            new TransactionRecord();

                    record.setType(entity.getType());
                    record.setSymbol(entity.getSymbol());
                    record.setShares(entity.getShares());
                    record.setPrice(entity.getPrice());
                    record.setDate(entity.getDate());
                    record.setRealizedGain(entity.getRealizedGain());

                    return record;
                })
                .collect(Collectors.toList());
    }
}