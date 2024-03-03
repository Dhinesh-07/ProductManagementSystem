package com.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailsService.class);

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }


    public OrderDetails getOrderDetailsById(int id) {
        return orderDetailsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("OrderDetails not found with id: " + id));
    }

    public OrderDetails addOrderDetails(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    public OrderDetails updateOrderDetails(int id, OrderDetails updatedOrderDetails) {
        Optional<OrderDetails> optionalOrderDetails = orderDetailsRepository.findById(id);
        if (optionalOrderDetails.isPresent()) {
            OrderDetails existingOrderDetails = optionalOrderDetails.get();
            if (updatedOrderDetails.getOrder() != null) {
                existingOrderDetails.setOrder(updatedOrderDetails.getOrder());
            }
            if (updatedOrderDetails.getProduct() != null) {
                existingOrderDetails.setProduct(updatedOrderDetails.getProduct());
            }

            existingOrderDetails.setQuantity(updatedOrderDetails.getQuantity());
            existingOrderDetails.setPrice(updatedOrderDetails.getPrice());
            return orderDetailsRepository.save(existingOrderDetails);
        } else {
            return null;
        }
    }

    public void deleteOrderDetails(int id) {
        OrderDetails existingOrderDetails = getOrderDetailsById(id);
        orderDetailsRepository.delete(existingOrderDetails);
    }
}

