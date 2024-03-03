package com.product;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<OrderEntity> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public OrderEntity addOrder(OrderEntity order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public OrderEntity updateOrder(Long id, OrderEntity updatedOrder) {
        Optional<OrderEntity> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            OrderEntity existingOrder = optionalOrder.get();
            existingOrder.setUserDetails(updatedOrder.getUserDetails());
            existingOrder.setDate(updatedOrder.getDate());
            existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
            return orderRepository.save(existingOrder);
        } else {
            // Handle order not found scenario
            return null;
        }
    }
}
