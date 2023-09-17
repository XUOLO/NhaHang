package com.example.sv.Service;


import com.example.sv.Model.ProductCategory;

import com.example.sv.Repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getAllProductCategory() {
        return productCategoryRepository.findAll();
    }

    public void saveProductCategory(ProductCategory productCategory) {
        this.productCategoryRepository.save(productCategory);

    }
}
