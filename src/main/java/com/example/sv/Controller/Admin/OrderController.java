package com.example.sv.Controller.Admin;

import com.example.sv.Model.Order;
import com.example.sv.Model.OrderDetail;
import com.example.sv.Model.Product;
import com.example.sv.Model.User;
import com.example.sv.Repository.OrderDetailRepository;
import com.example.sv.Repository.OrderRepository;
import com.example.sv.Repository.UserRepository;
import com.example.sv.Service.OrderDetailService;
import com.example.sv.Service.OrderService;
import com.example.sv.Service.RoleService;
import com.example.sv.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @GetMapping("/admin/list_order")
    public String showListOrder(Model model, Authentication authentication){
        String username = authentication.getName();
        model.addAttribute("listStaff", userService.getAllUser());
        model.addAttribute("listRole", roleService.getAllRole());
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("username", username);

        model.addAttribute("listOrder", orderService.getAllOrder());


        return "Admin/list_order";
    }


    @PostMapping("/admin/{id}/updateOrderStatus")
    public String updateOrderStatus(@PathVariable("id") Long id, @RequestParam("status") String status, Model model, HttpSession session, HttpServletRequest request) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order id: " + id));
        order.setStatus(status);
        orderRepository.save(order);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }


    @GetMapping("/admin/orderDetail/{id}")
    public String showOrderDetail(Authentication authentication,@PathVariable(value = "id") long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);

        // Lấy danh sách OrderDetail theo Order
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrder(order);
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("totalAmount",order.getTotal());

        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("username", username);

        return "Admin/order_detail";
    }



}
