package com.example.sv.Service;

import com.example.sv.Model.Product;
import com.example.sv.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {


    @Autowired
    private ProductRepository ProductRepository;

    public List<Product> getAllProduct() {
        return ProductRepository.findAll();
    }

    public void saveProduct(Product product) {
        this.ProductRepository.save(product);

    }
    public Product viewById(long id) {
        return ProductRepository.findById(id).get();
    }

}
