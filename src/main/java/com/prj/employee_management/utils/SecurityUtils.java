package com.prj.employee_management.utils;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.prj.employee_management.repositories.UserAccountRepo;

@Component
public class SecurityUtils {

    @Autowired
    private UserAccountRepo userAccountRepo;

    //incomignEmployeeId : id vient avec la requette envoyee par le user
    public boolean isOwner(UUID incomingEmployeeId) {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        String username = userDetails.getUsername();
        System.out.println("HERE" + incomingEmployeeId);

        return userAccountRepo.isOwner(username, incomingEmployeeId);
        
    }

}