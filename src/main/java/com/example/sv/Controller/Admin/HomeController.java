package com.example.sv.Controller.Admin;

import com.example.sv.Model.Product;
import com.example.sv.Model.User;
import com.example.sv.Repository.UserRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private  UserRepository userRepository;
    @GetMapping("/login")
    public String showLogin( Model model ) {

         return "Admin/login";
    }

    @GetMapping("/admin")
    public String index(Authentication authentication, Model model) {
        String username = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        boolean isEmployee = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_EMPLOYEE"));
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
