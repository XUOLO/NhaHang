package com.example.sv.Controller.Admin;

import com.example.sv.Model.Product;
import com.example.sv.Model.User;
import com.example.sv.Repository.UserRepository;
import com.example.sv.Service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @Autowired
    private  UserRepository userRepository;
    @GetMapping("/login")
    public String showLogin( Model model ) {

         return "Admin/login";
    }

    @PostMapping("/login")
    public void login( Model model,@RequestBody LoginRequest request, HttpSession session) {

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());


        Authentication authentication = authenticationManager.authenticate(token);


        SecurityContextHolder.getContext().setAuthentication(authentication);

        session.setAttribute("MY_SESSION", authentication.getName());


    }

    @GetMapping("/admin")
    public String index(Authentication authentication, Model model) {

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        boolean isEmployee = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_EMPLOYEE"));
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("username", username);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isEmployee", isEmployee);

        return "Admin/index";
    }

    @GetMapping("/welcome")
    public String greeting() {
        return "welcome";
    }



}
