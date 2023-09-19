package com.example.sv.Controller.Admin;

import com.example.sv.Model.Category;
import com.example.sv.Model.Product;
import com.example.sv.Model.ProductCategory;
import com.example.sv.Repository.CategoryRepository;
import com.example.sv.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CategoryService categoryService;

    @GetMapping("/admin/list_category")
    public String showListCategory(Model model) {



        model.addAttribute("listCategory", categoryService.getAllCategory());

        return "Admin/list_category";
    }

    @GetMapping("/admin/showFormForUpdateCategory/{id}")
    public String showFormForUpdateCategory(@PathVariable(value = "id") long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);

        return "Admin/update_category";
    }

    @GetMapping("/admin/deleteCategory/{id}")
    public String deleteCategory(@PathVariable(value = "id") long id) {
        this.categoryService.deleteCategoryById(id);
        return "redirect:/admin/list_category";
    }


    @PostMapping("/admin/updateCategory")
    public String updateCategory(@ModelAttribute("category") @Valid Category category, BindingResult bindingResult,
                                Model model
                                 ) throws IOException, SerialException, SQLException {



        categoryService.saveCategory(category);
        return "redirect:/admin/list_category";

    }


    @PostMapping("/admin/addCategory")
    public String addCategory(@Valid @ModelAttribute("productCategory") Category category, BindingResult bindingResult, Model model) throws IOException, SerialException, SQLException {



        categoryService.saveCategory(category);
        return "redirect:/admin/list_category";

    }

    @GetMapping("/admin/new_category")
    public String showNewCategory(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);

        return "Admin/new_category";
    }
}
