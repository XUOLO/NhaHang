package com.example.sv.Security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@AllArgsConstructor
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()

                .authorizeRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/admin/list_product").hasAnyAuthority("ROLE_EMPLOYEE", "ROLE_ADMIN")
                .requestMatchers("/admin/showFormForUpdateProduct/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/admin/new_product").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/admin/deleteProduct/**").hasAuthority("ROLE_ADMIN")
                    .anyRequest().authenticated()
                .and()
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error")
                        .defaultSuccessUrl("/admin")
                        .permitAll()
                )
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)

                        .permitAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/logout"))
                );

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}