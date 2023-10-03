package com.example.sv.Controller.User;

import com.example.sv.Model.*;
import com.example.sv.Repository.OrderDetailRepository;
import com.example.sv.Repository.OrderRepository;
import com.example.sv.Repository.ProductRepository;
import com.example.sv.Repository.UserRepository;
import com.example.sv.Service.CategoryService;
import com.example.sv.Service.OrderService;
import com.example.sv.Service.ShoppingCartService;
import com.example.sv.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Controller
public class CheckOutController
{
@Autowired
private OrderRepository orderRepository;
@Autowired
private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
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
    public String showCheckOut(Model model, Authentication authentication,Principal principal,HttpSession session) {

        if (authentication == null || !authentication.isAuthenticated()) {

            return "Admin/login";
        }
        Collection<CartItem> allCartItems = shoppingCartService.getAllCartItem();
        boolean isAuthenticated = principal != null;
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("AllCartItem", allCartItems);
        model.addAttribute("listCategory", categoryService.getAllCategory());
        model.addAttribute("totalAmount", shoppingCartService.getAmount());



        String username = (String) session.getAttribute("username");
        String name = (String) session.getAttribute("name");
        Long userId = (Long) session.getAttribute("userId");
        model.addAttribute("username", username);
        model.addAttribute("name", name);
        model.addAttribute("userId", userId);

        return "User/checkOut";
    }


    @PostMapping("/user/placeOrder")
    public String placeOrder(@RequestParam("name") String name,
                             @RequestParam("address") String address,
                             @RequestParam("phone") String phone,
                             @RequestParam("email") String email,
                             @ModelAttribute("order") Order order,
                             HttpSession session) {
         String username = (String) session.getAttribute("username");
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.viewById(userId);

        order.setName(name);
        order.setAddress(address);
        order.setPhone(phone);
        order.setEmail(email);
        order.setOrderDate(LocalDate.now());
        order.setUser(user);
        order.setTotal(shoppingCartService.getAmount());

        orderRepository.save(order);


         shoppingCartService.clear();

        return "User/checkOutSuccess";
    }




}
