package com.example.sv.Service;


import com.example.sv.Model.Product;
import com.example.sv.Model.ProductCategory;

import com.example.sv.Repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public ProductCategory getProductCategoryById(long id) {
        Optional<ProductCategory> optional = productCategoryRepository.findById(id);
        ProductCategory productCategory = null;
        if (optional.isPresent()) {
            productCategory = optional.get();
        }
        else
        {
            throw new RuntimeException(" Cant find product id : " + id);
        }
        return productCategory;
    }
    public void deleteProductCategoryById(long id) {
        this.productCategoryRepository.deleteById(id);
    }

}
