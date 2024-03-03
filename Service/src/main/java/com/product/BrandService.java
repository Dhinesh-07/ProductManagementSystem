package com.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public Brand saveProductBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public Optional<Brand> getProductBrandById(int id) {
        return brandRepository.findById(id);
    }

    public List<Brand> getAllProductBrands() {
        return brandRepository.findAll();
    }

    public Brand updateProductBrand(int id, Brand updatedBrand) {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (optionalBrand.isPresent()) {
            Brand existingBrand = optionalBrand.get();

            // Check if the updated brand has a category and set it to the existing brand
            if (updatedBrand.getCategory() != null) {
                existingBrand.setCategory(updatedBrand.getCategory());
            }

            // Check if the updated brand has a brand name and set it to the existing brand
            if (updatedBrand.getBrandName() != null) {
                existingBrand.setBrandName(updatedBrand.getBrandName());
            }

            // Check if the updated brand has a status and set it to the existing brand
            existingBrand.setStatus(updatedBrand.isStatus());

            // You can update other properties similarly as needed

            return brandRepository.save(existingBrand);
        } else {
            // Handle error, throw exception, or return null based on your requirement
            return null;
        }
    }


    public void deleteProductBrand(int id) {
        brandRepository.deleteById(id);
    }
}
