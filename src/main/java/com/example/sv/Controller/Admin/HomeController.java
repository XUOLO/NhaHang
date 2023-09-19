package com.example.sv.Controller.Admin;

import com.example.sv.Model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String showLogin( ) {


        return "Admin/login";
    }
    @GetMapping("/welcome")
    public String greeting() {
        return "welcome";
    }
}
