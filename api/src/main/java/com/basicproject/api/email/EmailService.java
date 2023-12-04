package com.basicproject.api.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {
    JavaMailSenderImpl mailSender;
    public EmailService(){
        this.initialize();
    }
    public void initialize(){
        this.mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.ethereal.email");
        mailSender.setPort(587);
        mailSender.setUsername("keon.kuvalis@ethereal.email");
        mailSender.setPassword("kHFDyTx6ZUNN8584JP");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable",true);

    }
    public void sendActivationEmail(String email,String activationToken){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("myapp@gmail.com");
        message.setTo(email);
        message.setSubject("Activation Mail");
        message.setText("http://localhost:3000/activation/"+activationToken);
        this.mailSender.send(message);
    }
}
