package com.product;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/stocks")
public class StockController {
    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        try {

            logger.info("Received request to fetch all stocks.");
            List<Stock> stockList = stockService.getAllStocks();
            logger.info("Fetched {} stocks.", stockList.size());
            return ResponseEntity.ok(stockList);
        } catch (Exception e) {
            logger.error("Error occurred while fetching all stocks: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable("id") Long id) {
        try {

            logger.info("Received request to fetch stock with id: {}", id);
            Stock stock = stockService.getStockById(id);
            return ResponseEntity.ok(stock);
        } catch (Exception e) {
            logger.error("Error occurred while fetching stock with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping
    public ResponseEntity<Stock> addStock(@RequestBody Stock stock, @RequestHeader(required = true) String userRole) {
        try {
            if (!userRole.equals("Admin")) {
                logger.warn("Unauthorized access to add stock: {}", stock);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            logger.info("Received request to add stock: {}", stock);
            Stock savedStock = stockService.addStock(stock);
            logger.info("Stock added successfully: {}", savedStock);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedStock);
        } catch (Exception e) {
            logger.error("Error occurred while adding stock: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable("id") Long id, @RequestBody Stock updatedStock, @RequestHeader(required = true) String userRole) {
        try {
            if (userRole != null && userRole.equals("Admin")) {
                logger.info("Received request to update stock with id: {}", id);
                Stock stock = stockService.updateStock(id, updatedStock);
                logger.info("Stock with id {} updated successfully: {}", id, stock);
                return ResponseEntity.ok(stock);
            } else {
                // Handle unauthorized access (userRole is not "Admin")
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            // Handle any exceptions
            logger.error("Error occurred while updating stock: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable("id") Long id, @RequestHeader(required = true) String userRole) {
        if (userRole != null && userRole.equals("Admin")) {
            try {
                logger.info("Received request to delete stock with id: {}", id);
                stockService.deleteStock(id);
                logger.info("Stock with id {} deleted successfully.", id);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                logger.error("Error occurred while deleting stock with id {}: {}", id, e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            logger.warn("Unauthorized access to delete stock with id {}", id);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
