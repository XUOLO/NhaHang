package com.example.sv.Controller;

import com.example.sv.Model.Product;
import com.example.sv.Model.ProductCategory;
import com.example.sv.Service.ProductCategoryService;
import com.example.sv.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Controller
public class ProductController {
    @Autowired
    private ProductService ProductService;
    @Autowired
    private ProductCategoryService ProductCategoryService;

    @GetMapping("/")
    public String index(){
        return "Admin/index";
    }
    @GetMapping("/display")
    public ResponseEntity<byte[]> displayImage(@RequestParam("id") long id) throws IOException, SQLException
    {
        Product product = ProductService.viewById(id);
        byte [] imageBytes = null;
        imageBytes = product.getImage().getBytes(1,(int) product.getImage().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
    @GetMapping("/list_product")
    public String showListProduct(Model model) {


        model.addAttribute("listProduct", ProductService.getAllProduct());
        model.addAttribute("listProductCategory", ProductCategoryService.getAllProductCategory());

        return "Admin/list_product";
    }

    @GetMapping("/new_product")
    public String showNewProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("listProductCategory",  ProductCategoryService.getAllProductCategory());
        return "Admin/new_product";
    }


    @PostMapping("/addProduct")
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
            ProductService.saveProduct(product);
            return "redirect:/list_product";
        }
    }


    @GetMapping("/list_productCategory")
    public String showListProductCategory(Model model) {


        model.addAttribute("listProductCategory", ProductCategoryService.getAllProductCategory());


        return "Admin/list_productCategory";
    }
    @GetMapping("/new_productCategory")
    public String showNewProductCategory(Model model) {
        ProductCategory productCategory = new ProductCategory();
        model.addAttribute("productCategory", productCategory);

        return "Admin/new_productCategory";
    }
    @PostMapping("/addProductCategory")
    public String addProductCategory(@Valid @ModelAttribute("productCategory") ProductCategory productCategory, BindingResult bindingResult, Model model) throws IOException, SerialException, SQLException {



            ProductCategoryService.saveProductCategory(productCategory);
            return "redirect:/list_productCategory";

    }
}
