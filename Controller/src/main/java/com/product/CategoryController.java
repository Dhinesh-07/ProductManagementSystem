package com.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @PostMapping


    public ResponseEntity<?> addCategory(@RequestBody Category category, @RequestHeader(required = true) String userRole) {
        if (userRole != null && userRole.equals("Admin")) {
            try {
                logger.info("Received request to add category: {}", category);
                Category savedCategory = categoryService.addCategory(category);
                logger.info("Category added successfully: {}", savedCategory);
                return ResponseEntity.ok(savedCategory);
            } catch (Exception e) {
                logger.error("Error occurred while adding category: {}", e.getMessage());
                e.printStackTrace(); // For debugging purposes
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            // Handle unauthorized access (userRole is not "Admin")

            logger.warn("Unauthorized access attempted to add category by user with role: {}", userRole);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @GetMapping
    public ResponseEntity<?> getAllCategories() {

            try {
                logger.info("Received request to fetch all categories.");
                List<Category> categories = categoryService.getAllCategories();
                return ResponseEntity.ok(categories);
            } catch (Exception e) {
                logger.error("Error occurred while fetching all categories: {}", e.getMessage());
                e.printStackTrace(); // For debugging purposes
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable int id) {

        try {
            logger.info("Received request to fetch category with id: {}", id);
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            logger.error("Error occurred while fetching category with id {}: {}", id, e.getMessage());
            e.printStackTrace(); // For debugging purposes
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable int id, @RequestBody Category category, @RequestHeader(required = true) String userRole) {
        if (userRole != null && userRole.equals("Admin")) {
            try {
                logger.info("Received request to update category with id: {}", id);
                Category updatedCategory = categoryService.updateCategory(id, category);
                return ResponseEntity.ok(updatedCategory);
            } catch (Exception e) {
                logger.error("Error occurred while updating category with id {}: {}", id, e.getMessage());
                e.printStackTrace(); // For debugging purposes
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            // Handle unauthorized access (userRole is not "Admin")
            logger.warn("Unauthorized access attempted to update category with id {} by user with role: {}", id, userRole);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id, @RequestHeader(required = true) String userRole) {
        if (userRole != null && userRole.equals("Admin")) {
            try {
                logger.info("Received request to delete category with id: {}", id);
                categoryService.deleteCategory(id);
                logger.info("Category with id {} deleted successfully", id);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                logger.error("Error occurred while deleting category with id {}: {}", id, e.getMessage());
                e.printStackTrace(); // For debugging purposes
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            // Handle unauthorized access (userRole is not "Admin")
            logger.warn("Unauthorized access attempted to delete category with id {} by user with role: {}", id, userRole);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
