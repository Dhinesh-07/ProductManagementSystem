package com.product;



import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderEntity> getAllOrders() {
        logger.info("Received request to fetch all orders.");
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> getOrderById(@PathVariable Long id) {
        logger.info("Received request to fetch order with id: {}", id);
        Optional<OrderEntity> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderEntity> addOrder(@RequestBody OrderEntity order) {
        logger.info("Received request to add order: {}", order);
        OrderEntity addedOrder = orderService.addOrder(order);
        logger.info("Order added successfully: {}", addedOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        logger.info("Received request to delete order with id: {}", id);
        orderService.deleteOrder(id);
        logger.info("Order with id {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderEntity> updateOrder(@PathVariable Long id, @RequestBody OrderEntity updatedOrder) {
        logger.info("Received request to update order with id: {}", id);
        logger.debug("Updated order details: {}", updatedOrder);

        OrderEntity order = orderService.updateOrder(id, updatedOrder);

        if (order != null) {
            logger.info("Order with id {} updated successfully: {}", id, order);
            return ResponseEntity.ok(order);
        } else {
            logger.warn("Order with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
    }
}

