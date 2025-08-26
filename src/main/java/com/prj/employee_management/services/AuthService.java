package com.prj.employee_management.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prj.employee_management.dtos.LoginRequestDTO;
import com.prj.employee_management.dtos.SignupRequestDTO;
import com.prj.employee_management.entities.Employee;
import com.prj.employee_management.entities.UserAccount;
import com.prj.employee_management.repositories.EmployeeRepo;
import com.prj.employee_management.repositories.UserAccountRepo;
import com.prj.employee_management.shared.CustomResponseException;
import com.prj.employee_management.utils.JwtHelper;

@Service
public class AuthService {

    @Autowired
    private UserAccountRepo userAccountRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired 
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    public void signup(SignupRequestDTO signupRequest, String token) {

        UserAccount account = new UserAccount();

        Employee employee = employeeRepo.findOneByAccountCreationToken(token)
            .orElseThrow(()-> CustomResponseException.ResourceNotFound("Invalid Token"));

        if(employee.isVerified()){
            throw CustomResponseException.BadRequest("Account already created");
        }

        account.setUsername(signupRequest.username());
        account.setPassword(passwordEncoder.encode(signupRequest.password()));
        account.setEmployee(employee);

        userAccountRepo.save(account);

        employee.setVerified(true);
        employee.setAccountCreationToken(null);
        employeeRepo.save(employee);
    }

    //retourn String car retourn le Token
    public String login(LoginRequestDTO loginRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.username(),
                loginRequest.password()
            )
        );

        UserAccount user = userAccountRepo.findOneByUsername(loginRequest.username())
            .orElseThrow(() -> CustomResponseException.BadCredentials());

        Map<String, Object> customClaims = new HashMap<>();
        //dans cette map on peut ajouter tous les info qu'on veut
        customClaims.put("userId", user.getId());
        return jwtHelper.generateToken(customClaims, user);
    }

}