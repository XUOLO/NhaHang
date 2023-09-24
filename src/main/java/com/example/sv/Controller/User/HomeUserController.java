package com.example.sv.Controller.User;

import com.example.sv.Model.Category;
import com.example.sv.Model.User;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

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

        model.addAttribute("listCategory",categoryService.getAllCategory());
        model.addAttribute("listProductCategory",productCategoryService.getAllProductCategory());
        model.addAttribute("listProduct",productService.getAllProduct());

        return "User/home";
    }


    @GetMapping("/user/category/{id}")
    public String getCategoryPage(@PathVariable("id") Long categoryId, Model model) {
        Category category = categoryService.getCategoryById(categoryId);

        String templateName =   "User/"+  category.getName().toLowerCase()  ;

        model.addAttribute("listProductCategory",productCategoryService.getAllProductCategory());
        model.addAttribute("listProduct",productService.getAllProduct());
        model.addAttribute("listCategory",categoryService.getAllCategory());


        model.addAttribute("category", category);
        return templateName;
    }

    @GetMapping("/user/login")
    public String userLogin(Principal principal, Model model) {

        return "User/login";

    }


}
