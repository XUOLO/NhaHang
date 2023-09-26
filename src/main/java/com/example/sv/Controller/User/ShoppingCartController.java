package com.example.sv.Controller.User;

import com.example.sv.Model.CartItem;
import com.example.sv.Model.Product;
import com.example.sv.Service.CategoryService;
import com.example.sv.Service.ProductService;
import com.example.sv.Service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

        @Autowired
        private CategoryService categoryService;
    @Autowired
    ProductService productService;

    @GetMapping("/user/viewCart")
    public String viewCart(Model model){
        model.addAttribute("AllCartItem",shoppingCartService.getAllCartItem());
        model.addAttribute("listCategory", categoryService.getAllCategory());
        model.addAttribute("totalAmount",shoppingCartService.getAmount());
        return "User/ShoppingCart";
    }
    @GetMapping("/user/shoppingCart/add/{id}")
    public String shoppingCartAdd(@PathVariable("id") Integer id ,Model model){
        Product product= productService.getProductById(id);
        if(product!=null){
            CartItem cartItem = new CartItem();
            cartItem.setProductId(product.getId());
            cartItem.setName(product.getName());
            cartItem.setPrice(product.getPrice());
            cartItem.setQuantity(product.getQuantity());
            shoppingCartService.add(cartItem);
         }

        return "redirect:/user/viewCart";
    }


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
