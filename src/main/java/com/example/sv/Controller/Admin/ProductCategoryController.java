package com.example.sv.Controller.Admin;

import com.example.sv.Model.Product;
import com.example.sv.Model.ProductCategory;
import com.example.sv.Repository.ProductRepository;
import com.example.sv.Service.ProductCategoryService;
import com.example.sv.Service.ProductService;
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
public class ProductCategoryController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryService productCategoryService;


    @GetMapping("/admin/list_productCategory")
    public String showListProductCategory(Model model) {


        model.addAttribute("listProductCategory", productCategoryService.getAllProductCategory());


        return "Admin/list_productCategory";
    }

    @GetMapping("/admin/new_productCategory")
    public String showNewProductCategory(Model model) {
        ProductCategory productCategory = new ProductCategory();
        model.addAttribute("productCategory", productCategory);

        return "Admin/new_productCategory";
    }
    @PostMapping("/admin/addProductCategory")
    public String addProductCategory(@Valid @ModelAttribute("productCategory") ProductCategory productCategory, BindingResult bindingResult, Model model) throws IOException, SerialException, SQLException {



        productCategoryService.saveProductCategory(productCategory);
        return "redirect:/admin/list_productCategory";

    }


    @GetMapping("/admin/showFormForUpdateProductCategory/{id}")
    public String showFormForUpdateProductCategory(@PathVariable(value = "id") long id, Model model) {
        ProductCategory productCategory = productCategoryService.getProductCategoryById(id);
        model.addAttribute("productCategory", productCategory);

        return "Admin/update_productCategory";
    }
    @GetMapping("/admin/deleteProductCategory/{id}")
    public String deleteProduct(@PathVariable(value = "id") long id) {
        this.productCategoryService.deleteProductCategoryById(id);
        return "redirect:/admin/list_productCategory";
    }

    @PostMapping("/admin/updateProductCategory")
    public String updateProduct(@ModelAttribute("productCategory") @Valid  ProductCategory productCategory, BindingResult bindingResult,
                                Model model
                              ) throws IOException, SerialException, SQLException {



        productCategoryService.saveProductCategory(productCategory);
        return "redirect:/admin/list_productCategory";

    }
}
