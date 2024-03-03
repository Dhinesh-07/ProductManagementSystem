package com.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock getStockById(Long id) {
        Optional<Stock> optionalStock = stockRepository.findById(id);
        if (optionalStock.isPresent()) {
            return optionalStock.get();
        } else {
            throw new NoSuchElementException("Stock not found with id: " + id);
        }
    }

    public Stock addStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Stock updateStock(Long id, Stock updatedStock) {
        Stock existingStock = getStockById(id);
        // Update existingStock with fields from updatedStock
        existingStock.setProduct(updatedStock.getProduct());
        existingStock.setStock(updatedStock.getStock());
        return stockRepository.save(existingStock);
    }

    public void deleteStock(Long id) {
        Stock existingStock = getStockById(id);
        stockRepository.delete(existingStock);
    }
}
