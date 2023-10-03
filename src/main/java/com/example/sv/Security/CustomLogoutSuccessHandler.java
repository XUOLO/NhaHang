package com.example.sv.Security;

import com.example.sv.Service.ShoppingCartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private ShoppingCartService shoppingCartService;

    public CustomLogoutSuccessHandler(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        if (authentication != null && authentication.getAuthorities() != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    response.sendRedirect("/login?logout");
                    return;
                } else if (authority.getAuthority().equals("ROLE_EMPLOYEE")) {
                    response.sendRedirect("/login?logout");
                    return;
                } else if (authority.getAuthority().equals("ROLE_USER")) {
                    shoppingCartService.clear();
                    response.sendRedirect("/");
                    return;
                }
            }
        }


    }
}