package com.example.sv.Controller.User;

import com.example.sv.Model.Product;
import com.example.sv.Repository.ProductCategoryRepository;
import com.example.sv.Repository.ProductRepository;
import com.example.sv.Service.CategoryService;
import com.example.sv.Service.ProductCategoryService;
import com.example.sv.Service.ProductService;
import com.example.sv.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @GetMapping("/user/menu")
    public String viewMenu(Model model){

        List<Product> productList = productService.getAllProduct();
        List<Product> sellingProducts = new ArrayList<>();

        for (Product product : productList) {
            if ("1".equals(product.getStatus())) {
                sellingProducts.add(product);
            }
        }

        model.addAttribute("listCategory", categoryService.getAllCategory());
        model.addAttribute("listProductCategory", productCategoryService.getAllProductCategory());
        model.addAttribute("listProduct", sellingProducts);
        return "User/menu";
    }
}
