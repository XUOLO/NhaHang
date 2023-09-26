package com.example.sv.Service;

import com.example.sv.Model.Product;
import com.example.sv.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Product getProductById(long id) {
        Optional<Product> optional = ProductRepository.findById(id);
        Product product = null;
        if (optional.isPresent()) {
            product = optional.get();
        }
        else
        {
            throw new RuntimeException(" Cant find product id : " + id);
        }
        return product;
    }

    public void deleteProductById(long id) {
        this.ProductRepository.deleteById(id);
    }

}
