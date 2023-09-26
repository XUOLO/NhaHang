package com.example.sv.Controller.User;

import com.example.sv.Model.CartItem;
import com.example.sv.Model.Product;
import com.example.sv.Service.CategoryService;
import com.example.sv.Service.ProductService;
import com.example.sv.Service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@Controller
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

        @Autowired
        private CategoryService categoryService;
    @Autowired
    ProductService productService;

    @GetMapping("/user/viewCart")
    public String viewCart(Model model) {
        Collection<CartItem> allCartItems = shoppingCartService.getAllCartItem();

        model.addAttribute("AllCartItem", allCartItems);
        model.addAttribute("listCategory", categoryService.getAllCategory());
        model.addAttribute("totalAmount", shoppingCartService.getAmount());

        boolean hasItems = !allCartItems.isEmpty();
        model.addAttribute("hasItems", hasItems);

        return "User/ShoppingCart";
    }

//    @GetMapping("/user/shoppingCart/add/{id}")
//    public String shoppingCartAdd(@PathVariable("id") Integer id) {
//        Product product = productService.getProductById(id);
//        if (product != null) {
//            CartItem cartItem = new CartItem();
//            cartItem.setProductId(product.getId());
//            cartItem.setName(product.getName());
//            cartItem.setPrice(product.getPrice());
//            cartItem.setQuantity(product.getQuantity());
//            cartItem.setImage(product.getImage());
//            cartItem.setProductCategory(product.getProductCategory().getName());
//            shoppingCartService.add(cartItem);
//
//            return "success"; // Trả về một phản hồi JSON hoặc thông báo thành công
//        }
//
//        return "error"; // Trả về một phản hồi JSON hoặc thông báo lỗi
//    }


    @GetMapping("/user/shoppingCart/clear")
    public String clearCart(Model model){
         shoppingCartService.clear();
        return "redirect:/user/viewCart";
    }


    @GetMapping("/user/shoppingCart/remove/{id}")
    public String removeItemCart(@PathVariable("id") int id){
        shoppingCartService.remove(id);
        return "redirect:/user/viewCart";
    }
    @PostMapping("/user/shoppingCart/updateCart")
    public String updateItemCart(@RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity){
        shoppingCartService.update(productId, quantity);
        return "redirect:/user/viewCart";
    }

}
