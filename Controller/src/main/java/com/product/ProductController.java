package com.product;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {

        try {
            logger.info("Received request to fetch all products.");
            List<Product> products = productService.getAllProducts();
            logger.info("Fetched {} products.", products.size());
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error occurred while fetching all products: {}", e.getMessage());
            e.printStackTrace(); // For debugging purposes
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id) {

        try {
            logger.info("Received request to fetch product with id: {}", id);
            Optional<Product> product = productService.getProductById(id);
            return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error occurred while fetching product with id {}: {}", id, e.getMessage());
            e.printStackTrace(); // For debugging purposes
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        logger.info("Received request to add product: {}", product);
        Product addedProduct = productService.addProduct(product);
        logger.info("Product added successfully: {}", addedProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id, @RequestHeader(required = true) String userRole) {
        if (userRole != null && userRole.equals("Admin")) {
            try {
                logger.info("Received request to delete product with id: {}", id);
                productService.deleteProduct(id);
                logger.info("Product with id {} deleted successfully.", id);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                logger.error("Error occurred while deleting product with id {}: {}", id, e.getMessage());
                e.printStackTrace(); // For debugging purposes
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            // Handle unauthorized access (userRole is not "Admin")
            logger.warn("Unauthorized access attempted to delete product with id {} by user with role: {}", id, userRole);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product updatedProduct, @RequestHeader(required = true) String userRole) {
        if (userRole != null && userRole.equals("Admin")) {
            try {
                logger.info("Received request to update product with id: {}", id);
                Product product = productService.updateProduct(id, updatedProduct);
                if (product != null) {
                    return ResponseEntity.ok(product);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e) {
                logger.error("Error occurred while updating product with id {}: {}", id, e.getMessage());
                e.printStackTrace(); // For debugging purposes
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            // Handle unauthorized access (userRole is not "Admin")
            logger.warn("Unauthorized access attempted to update product with id {} by user with role: {}", id, userRole);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}

