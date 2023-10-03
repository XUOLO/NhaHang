package com.example.sv.Controller.User;

import com.example.sv.Model.CartItem;
import com.example.sv.Model.Order;
import com.example.sv.Model.Product;
import com.example.sv.Model.User;
import com.example.sv.Repository.OrderRepository;
import com.example.sv.Repository.ProductRepository;
import com.example.sv.Repository.UserRepository;
import com.example.sv.Service.CategoryService;
import com.example.sv.Service.ShoppingCartService;
import com.example.sv.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
public class CheckOutController
{
@Autowired
private OrderRepository orderRepository;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductRepository productRepository;

//    @GetMapping("/user/checkOut")
//    public String showCheckOut(Model model){
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//
//            return "Admin/login";
//        }
//        Collection<CartItem> allCartItems = shoppingCartService.getAllCartItem();
//
//        model.addAttribute("AllCartItem", allCartItems);
//        model.addAttribute("listCategory", categoryService.getAllCategory());
//        model.addAttribute("totalAmount", shoppingCartService.getAmount());
//        return "User/checkOut";
//    }
    @PostMapping("/user/checkOut")
    public String showCheckOut(Model model, Authentication authentication,Principal principal) {

        if (authentication == null || !authentication.isAuthenticated()) {

            return "Admin/login";
        }
        Collection<CartItem> allCartItems = shoppingCartService.getAllCartItem();
        boolean isAuthenticated = principal != null;
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("AllCartItem", allCartItems);
        model.addAttribute("listCategory", categoryService.getAllCategory());
        model.addAttribute("totalAmount", shoppingCartService.getAmount());
        return "User/checkOut";
    }


    @PostMapping("/checkout")
    public String placeOrder(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("name") String name,
                             @RequestParam("address") String address,
                             @RequestParam("phone") String phone,
                             @RequestParam("email") String email,
                             @RequestParam("products") List<Long> productIds) {



        Long userId = Long.valueOf(userDetails.getUsername());
        Order order = new Order();
        order.setName(name);
        order.setAddress(address);
        order.setPhone(phone);
        order.setEmail(email);




        List<Product> products = productRepository.findAllById(productIds);
        order.setProducts(products);

        // Save the order to the database
        orderRepository.save(order);

        // Redirect to a success page or perform other necessary actions
        return "User/checkOutSuccess";
    }




}
