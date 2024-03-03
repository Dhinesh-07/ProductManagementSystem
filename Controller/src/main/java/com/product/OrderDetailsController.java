package com.product;


import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/orderdetails")
public class OrderDetailsController {
    private static final Logger logger = LoggerFactory.getLogger(OrderDetailsController.class);

    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping
    public ResponseEntity<List<OrderDetails>> getAllOrderDetails() {
        logger.info("Received request to fetch all order details.");
        List<OrderDetails> orderDetailsList = orderDetailsService.getAllOrderDetails();
        logger.info("Fetched {} order details.", orderDetailsList.size());
        return ResponseEntity.ok(orderDetailsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetails> getOrderDetailsById(@PathVariable("id") int id) {
        logger.info("Received request to fetch order details with id: {}", id);

        OrderDetails orderDetails = orderDetailsService.getOrderDetailsById(id);

        if (orderDetails != null) {
            logger.info("Order details with id {} found: {}", id, orderDetails);
            return ResponseEntity.ok(orderDetails);
        } else {
            logger.warn("Order details with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<OrderDetails> addOrderDetails(@RequestBody OrderDetails orderDetails) {
        logger.info("Received request to add order details: {}", orderDetails);
        OrderDetails savedOrderDetails = orderDetailsService.addOrderDetails(orderDetails);
        logger.info("Order details added successfully: {}", savedOrderDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrderDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetails> updateOrderDetails(@PathVariable("id") int id, @RequestBody OrderDetails updatedOrderDetails) {
        logger.info("Received request to update order details with id: {}", id);
        logger.debug("Updated order details: {}", updatedOrderDetails);

        OrderDetails orderDetails = orderDetailsService.updateOrderDetails(id, updatedOrderDetails);

        if (orderDetails != null) {
            logger.info("Order details with id {} updated successfully: {}", id, orderDetails);
            return ResponseEntity.ok(orderDetails);
        } else {
            logger.warn("Order details with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetails(@PathVariable("id") int id) {
        logger.info("Received request to delete order details with id: {}", id);
        orderDetailsService.deleteOrderDetails(id);
        logger.info("Order details with id {} deleted successfully.", id);
        return ResponseEntity.noContent().build();
    }
}