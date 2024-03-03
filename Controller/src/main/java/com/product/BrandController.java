package com.product;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/brands")
public class BrandController {
    private static final Logger logger = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    private BrandService brandService;


    @PostMapping
    public ResponseEntity<Brand> addBrand(@RequestBody Brand brand) {

        try {
            Brand savedBrand = brandService.saveProductBrand(brand);
            return ResponseEntity.ok(savedBrand);
        } catch (ServiceException e) {
            logger.error("Error occurred while adding product brand: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {

        List<Brand> brands = brandService.getAllProductBrands();
        return ResponseEntity.ok(brands);

    }


    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable int id) {

            Optional<Brand> brand = brandService.getProductBrandById(id);
            return brand.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }


    @PutMapping("/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable int id, @RequestBody Brand brand, @RequestHeader(required = true) String userRole) {
        if (userRole != null && userRole.equals("Admin")) {
            try {
                Brand updatedBrand = brandService.updateProductBrand(id, brand);
                return ResponseEntity.ok(updatedBrand);
            } catch (ServiceException e) {
                logger.error("Error occurred while updating product brand with ID {}: {}", id, e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            // Handle unauthorized access (userRole is not "Admin")
            logger.warn("Unauthorized access attempted to update product brand with ID {} by user with role: {}", id, userRole);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable int id, @RequestHeader(required = true) String userRole) {
        if (userRole != null && userRole.equals("Admin")) {
            try {
                brandService.deleteProductBrand(id);
                logger.info("Product brand with ID {} deleted successfully.", id);
                return ResponseEntity.noContent().build();
            } catch (ServiceException e) {
                logger.error("Error occurred while deleting product brand with ID {}: {}", id, e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            // Handle unauthorized access (userRole is not "Admin")
            logger.warn("Unauthorized access attempted to delete product brand with ID {} by user with role: {}", id, userRole);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
