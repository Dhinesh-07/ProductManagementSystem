package com.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.orElse(null); // or throw an exception if appropriate
    }

    public Category updateCategory(int id, Category updatedCategory) {

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            if (updatedCategory.getTitle() != null) {
                existingCategory.setTitle(updatedCategory.getTitle());
            }
            if (updatedCategory.isStatus() != existingCategory.isStatus()) {
                existingCategory.setStatus(updatedCategory.isStatus());
            }

            return categoryRepository.save(existingCategory);
        } else {
            // Handle the case where the category with the given ID does not exist
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id);
        }
    }

    public void deleteCategory(int id) {
        // Check if the category exists
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            // Delete the category
            categoryRepository.deleteById(id);
        } else {
            // Handle the case where the category with the given ID does not exist
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id);
        }
    }
}

