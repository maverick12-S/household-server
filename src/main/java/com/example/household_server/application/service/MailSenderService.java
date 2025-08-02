package com.example.household_server.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String to,String subject,String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("shiba14148@gmail.com");
        message.setSubject(subject);
        message.setText(body);
        try{
            
        mailSender.send(message);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
