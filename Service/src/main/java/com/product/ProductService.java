package com.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockRepository stockRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    public Product addProduct(Product product) {
        if (product.getStock() == null) {
            // If the product doesn't have a stock, create a new stock
            Stock stock = new Stock();
            stock.setStock(0); // Initialize stock to 0 or any default value
            stock = stockRepository.save(stock); // Save the new stock
            product.setStock(stock); // Set the stock for the product
        }
        return productRepository.save(product);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(int id, Product updatedProduct) {
        // Retrieve the existing product from the repository
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            // Update the product fields if they are provided in the updatedProduct object
            if (updatedProduct.getProductName() != null) {
                existingProduct.setProductName(updatedProduct.getProductName());
            }
            if (updatedProduct.getDescription() != null) {
                existingProduct.setDescription(updatedProduct.getDescription());
            }
            if (updatedProduct.getCategory() != null) {
                existingProduct.setCategory(updatedProduct.getCategory());
            }
            if (updatedProduct.getBrands() != null) {
                existingProduct.setBrands(updatedProduct.getBrands());
            }
            if (updatedProduct.getStock() != null) {
                existingProduct.setStock(updatedProduct.getStock());
            }
            if (updatedProduct.getMrp() >= 0) {
                existingProduct.setMrp(updatedProduct.getMrp());
            }

            if (updatedProduct.getSalePrice() >= 0) {
                existingProduct.setSalePrice(updatedProduct.getSalePrice());
            }

            // Save the updated product back to the repository
            return productRepository.save(existingProduct);
        } else {
            // Handle the scenario where the product with the specified id is not found
            throw new ServiceException("Product not found with id: " + id);
        }
    }

}


