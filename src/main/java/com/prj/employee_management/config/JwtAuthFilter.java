package com.prj.employee_management.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.prj.employee_management.utils.JwtHelper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("Filter IS RUNNING.... ");
        //le client envoie un Header Authorization="Bearer er5njdvmv...."
        //dans ce filter en prend la valeur exister dans le Header et verifer est ce que cet token est correct
        String authHeader = request.getHeader("Authorization");
        System.out.println("header= " + authHeader);
        String token = null;

        //verifier est ce que le header n'est pas vide et commence par "Bearer "
        if(authHeader != null && authHeader.startsWith("Bearer ")){

            //recuper le token depuis le header(supprimer les 7 premier char : "Bearer ")
            token = authHeader.substring(7);

        }

        //si le token null passez ou filter suivant
        if(token == null){
            filterChain.doFilter(request, response);
            return;
        }

        
        //extraire le username quand le token n'est pas null
        String username = jwtHelper.extractUsername(token);

        if(username != null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Boolean isTokenValid = jwtHelper.isTokenValid(token, userDetails);
            if(isTokenValid){

                //===Construire le Authentication Context
                //si le token valid donc on fait l'authentification
                var authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, 
                    null, // credentials == null
                    userDetails.getAuthorities()
                    ); 
                
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}