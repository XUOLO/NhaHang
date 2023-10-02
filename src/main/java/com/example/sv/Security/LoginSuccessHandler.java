package com.example.sv.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Lấy role của người dùng
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
        String role = authorities.iterator().next().getAuthority();

        // Chuyển hướng người dùng đến trang tương ứng với role
        if (role.equals("ROLE_ADMIN")) {
            response.sendRedirect("/admin");
        } else if (role.equals("ROLE_EMPLOYEE")) {
            response.sendRedirect("/admin");
        } else {
            response.sendRedirect("/");
        }
    }
}