package com.example.sv.Security;

import com.example.sv.Model.User;
import com.example.sv.Service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private  UserService userService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Lấy role của người dùng
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
        String role = authorities.iterator().next().getAuthority();

        // Lấy tên người dùng
        String username = authentication.getName();

        // Tạo đường dẫn chuyển hướng với tên người dùng
        String redirectUrl;
        if (role.equals("ROLE_ADMIN")) {
            redirectUrl = "/admin";
        } else if (role.equals("ROLE_EMPLOYEE")) {
            redirectUrl = "/admin";
        } else {
            redirectUrl = "/";
        }
        User user = userService.findUserByUsername(username);

        // Lưu tên người dùng vào Model
        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute("name", user.getName());

        // Chuyển hướng người dùng đến đường dẫn tương ứng
        response.sendRedirect(redirectUrl);
    }
}