package com.prj.employee_management.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prj.employee_management.entities.UserAccount;
import com.prj.employee_management.repositories.UserAccountRepo;
import com.prj.employee_management.shared.CustomResponseException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{


    @Autowired
    private UserAccountRepo userAccountRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> account = userAccountRepo.findOneByUsername(username);

        if(account.isEmpty()){
            throw CustomResponseException.BadCredentials();
        }

        UserAccount user = account.get();

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();

    }


}