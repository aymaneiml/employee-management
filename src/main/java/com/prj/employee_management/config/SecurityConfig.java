package com.prj.employee_management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.prj.employee_management.services.AuthService;
import com.prj.employee_management.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {



    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(c->c.disable())
             .csrf(c->c.disable())
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers(
                    "/auth/signup",
                    "/auth/login"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, "/employees").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/employees/{employeeId}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/employees").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/employees/{employeeId}").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/employees/{employeeId}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST,"/employees/{employeeId}/leave-requests").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/employees/{employeeId}/leave-requests").hasAnyRole("ADMIN", "USER")
                .anyRequest()
                .authenticated()
                ;
            })
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .authenticationManager(authenticationManager(http));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        var authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        return authBuilder.build();
    }

}