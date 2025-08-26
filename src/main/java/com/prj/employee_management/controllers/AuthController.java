package com.prj.employee_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prj.employee_management.dtos.LoginRequestDTO;
import com.prj.employee_management.dtos.SignupRequestDTO;
import com.prj.employee_management.services.AuthService;
import com.prj.employee_management.shared.GlobalResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<GlobalResponse<String>> signup(
        @RequestBody SignupRequestDTO signupRequest,
        @RequestParam String token
    ) {
        System.out.println("token"  + token);
        authService.signup(signupRequest, token);
        return new ResponseEntity<>(new GlobalResponse<>("Signed UP"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<String>> login(
        @RequestBody LoginRequestDTO loginRequest
    ) {
        String token = authService.login(loginRequest);
        return new ResponseEntity<>(new GlobalResponse<>(token), HttpStatus.OK);
    }


}