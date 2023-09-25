package com.example.sv.Controller.Admin;

import com.example.sv.Model.Category;
import com.example.sv.Model.Product;
import com.example.sv.Model.ProductCategory;
import com.example.sv.Model.User;
import com.example.sv.Repository.CategoryRepository;
import com.example.sv.Repository.UserRepository;
import com.example.sv.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/list_category")
    public String showListCategory(Authentication authentication, Model model) {


        String username = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        boolean isEmployee = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_EMPLOYEE"));
        User user = userRepository.findByUsername(username);

        model.addAttribute("user", user);
        model.addAttribute("username", username);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isEmployee", isEmployee);
        model.addAttribute("listCategory", categoryService.getAllCategory());

        return "Admin/list_category";
    }

    @GetMapping("/admin/showFormForUpdateCategory/{id}")
    public String showFormForUpdateCategory(Authentication authentication,@PathVariable(value = "id") long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("username", username);

        model.addAttribute("user", user);
        return "Admin/update_category";
    }

    @GetMapping("/admin/deleteCategory/{id}")
    public String deleteCategory(@PathVariable(value = "id") long id) {
        this.categoryService.deleteCategoryById(id);
        return "redirect:/admin/list_category";
    }


    @PostMapping("/admin/updateCategory")
    public String updateCategory(@ModelAttribute("category") @Valid Category category,Authentication authentication, BindingResult bindingResult,
                                Model model
                                 ) throws IOException, SerialException, SQLException {

        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        categoryService.saveCategory(category);
        return "redirect:/admin/list_category";

    }


    @PostMapping("/admin/addCategory")
    public String addCategory(Authentication authentication,@Valid @ModelAttribute("productCategory") Category category, BindingResult bindingResult, Model model) throws IOException, SerialException, SQLException {


        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("username", username);

        categoryService.saveCategory(category);
        return "redirect:/admin/list_category";

    }

    @GetMapping("/admin/new_category")
    public String showNewCategory(Authentication authentication,Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("username", username);

        model.addAttribute("user", user);
        return "Admin/new_category";
    }
}
