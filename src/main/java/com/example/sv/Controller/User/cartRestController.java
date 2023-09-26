package com.example.sv.Controller.User;

import com.example.sv.Model.CartItem;
import com.example.sv.Model.Product;
import com.example.sv.Service.CategoryService;
import com.example.sv.Service.ProductService;
import com.example.sv.Service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class cartRestController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    ProductService productService;

    @GetMapping("/user/shoppingCart/add/{id}")
    public String shoppingCartAdd(@PathVariable("id") Integer id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            CartItem cartItem = new CartItem();
            cartItem.setProductId(product.getId());
            cartItem.setName(product.getName());
            cartItem.setPrice(product.getPrice());
            cartItem.setQuantity(product.getQuantity());
            cartItem.setImage(product.getImage());
            cartItem.setProductCategory(product.getProductCategory().getName());
            shoppingCartService.add(cartItem);

            return "success"; // Trả về một phản hồi JSON hoặc thông báo thành công
        }

        return "error"; // Trả về một phản hồi JSON hoặc thông báo lỗi
    }
}
