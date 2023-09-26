package com.example.sv.Controller.User;

import com.example.sv.Model.Category;
import com.example.sv.Model.Contact;
import com.example.sv.Model.Product;
import com.example.sv.Model.User;
import com.example.sv.Repository.ProductCategoryRepository;
import com.example.sv.Repository.ProductRepository;
import com.example.sv.Service.CategoryService;
import com.example.sv.Service.ProductCategoryService;
import com.example.sv.Service.ProductService;
import com.example.sv.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeUserController {

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



    @GetMapping("/")
    public String showIndexUser(Model model) {
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

        return "User/home";
    }


    @GetMapping("/user/category/{id}")
    public String getCategoryPage(@PathVariable("id") Long categoryId, Model model) {
        Category category = categoryService.getCategoryById(categoryId);
        Contact contact= new Contact();
        List<Product> productList = productService.getAllProduct();
        String templateName =   "User/"+  category.getName().toLowerCase()  ;

        model.addAttribute("listProductCategory",productCategoryService.getAllProductCategory());
        model.addAttribute("listProduct",productService.getAllProduct());
        model.addAttribute("listCategory",categoryService.getAllCategory());
        List<Product> sellingProducts = new ArrayList<>();

        for (Product product : productList) {
            if ("1".equals(product.getStatus())) {
                sellingProducts.add(product);
            }
        }
        model.addAttribute("listProduct", sellingProducts);

        model.addAttribute("contact", contact);

        model.addAttribute("category", category);
        return templateName;
    }

    @GetMapping("/user/login")
    public String userLogin(Principal principal, Model model) {

        return "User/login";

    }
    @GetMapping("/user/international")
    public String international(  HttpServletRequest request   ) {

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;

    }

}
