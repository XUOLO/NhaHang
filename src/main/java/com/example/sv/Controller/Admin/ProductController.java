package com.example.sv.Controller.Admin;

import com.example.sv.Model.Product;
import com.example.sv.Model.ProductCategory;
import com.example.sv.Repository.ProductRepository;
import com.example.sv.Service.ProductCategoryService;
import com.example.sv.Service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;

@Controller
public class ProductController {
    @Autowired
    private ProductService ProductService;
    @Autowired
    private ProductCategoryService ProductCategoryService;
    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/display")
    public ResponseEntity<byte[]> displayImage(@RequestParam("id") long id) throws IOException, SQLException
    {
        Product product = ProductService.viewById(id);
        byte [] imageBytes = null;
        imageBytes = product.getImage().getBytes(1,(int) product.getImage().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
    @GetMapping("/admin/list_product")
    public String showListProduct(Model model) {



        model.addAttribute("listProduct", ProductService.getAllProduct());
        model.addAttribute("listProductCategory", ProductCategoryService.getAllProductCategory());

        return "Admin/list_product";
    }


    @GetMapping("/admin/new_product")
    public String showNewProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("listProductCategory",  ProductCategoryService.getAllProductCategory());
        return "Admin/new_product";
    }


    @PostMapping("/admin/addProduct")
    public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, @RequestParam("image") MultipartFile file, Model model) throws IOException, SerialException, SQLException {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("listCategory", CategoryService.getAllCategory());
//            return "Admin/new_product";
//        } else dang bi loi binding image

        {
            byte[] bytes = file.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            product.setImage(blob);
            product.setCreateTime(LocalDateTime.now());
            product.setStatus("2");
            ProductService.saveProduct(product);
            return "redirect:/admin/list_product";
        }
    }


    @GetMapping("/admin/list_productCategory")
    public String showListProductCategory(Model model) {


        model.addAttribute("listProductCategory", ProductCategoryService.getAllProductCategory());


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



            ProductCategoryService.saveProductCategory(productCategory);
            return "redirect:/admin/list_productCategory";

    }


    @PostMapping("/admin/{id}/updateStatus")
    public String updateProductStatus(@PathVariable("id") Long id, @RequestParam("status") String status, Model model, HttpSession session, HttpServletRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ticket id: " + id));
        product.setStatus(status);
        productRepository.save(product);
        String referer = request.getHeader("Referer");


        return "redirect:" + referer;
    }



    @PostMapping("/admin/updateProduct")
    public String updateProduct(@ModelAttribute("product") @Valid  Product product, BindingResult bindingResult,
                           Model model,
                           @RequestParam("image") MultipartFile file) throws IOException, SerialException, SQLException {

                byte[] bytes = file.getBytes();
                Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
                product.setImage(blob);

            ProductService.saveProduct(product);
            return "redirect:/admin/list_product";

    }
    @GetMapping("/admin/showFormForUpdateProduct/{id}")
    public String showFormForUpdateSP(@PathVariable(value = "id") long id, Model model) {
        Product product = ProductService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("listProductCategory", ProductCategoryService.getAllProductCategory());

        return "Admin/update_product";
    }
    @GetMapping("/admin/deleteProduct/{id}")
    public String deleteProduct(@PathVariable(value = "id") long id) {
        this.ProductService.deleteProductById(id);
        return "redirect:/admin/list_product";
    }




}
