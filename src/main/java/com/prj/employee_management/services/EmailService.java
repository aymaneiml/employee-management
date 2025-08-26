package com.prj.employee_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${backend.origin}")
    private String ORIGIN;

    @Value("${spring.mail.username}")
    private String from;

    public void sendAccountCreationEmail(String to, String token) {

        String link = ORIGIN + "/auth/signup?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Create Your Account!");
        message.setText("Hi! PLease create your acc using this link: \n" + link);
        mailSender.send(message);
    }
}