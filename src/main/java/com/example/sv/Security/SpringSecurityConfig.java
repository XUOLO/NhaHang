package com.example.sv.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
@AllArgsConstructor
public class SpringSecurityConfig   {
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    private UserDetailsService userDetailsService;
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public LogoutSuccessHandler logoutSuccessHandler() {
//        return new LogoutSuccessHandler() {
//
//            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//                String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
//
//                if (role.equals("ROLE_ADMIN") ||role.equals("ROLE_EMPLOYEE")) {
//                    response.sendRedirect("/");
//                } else {
//                    response.sendRedirect("/");
//                }
//            }
//        };
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()

                .authorizeRequests()
                .requestMatchers("/").permitAll()

                .requestMatchers("/Admin/css/**", "/Admin/js/**", "/Admin/img/**", "/Admin/vendor/**", "/Admin/scss/**").permitAll()
                .requestMatchers("/User/css/**", "/User/js/**", "/User/images/**", "/User/fonts/**").permitAll()
                .requestMatchers("/admin/deleteProduct/*").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/user/**").permitAll()

                .requestMatchers("/admin/**").hasAnyAuthority("ROLE_EMPLOYEE", "ROLE_ADMIN")

                .requestMatchers("/display").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error")
                        .successHandler(loginSuccessHandler)
                        .permitAll()
                )
                .logout(logout -> logout.logoutUrl("/logout")
//                        .logoutSuccessHandler(logoutSuccessHandler())
                                .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)

                        .permitAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/logout"))
                ) ;


        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}