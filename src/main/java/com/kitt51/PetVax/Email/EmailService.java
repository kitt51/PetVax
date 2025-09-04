package com.kitt51.PetVax.Email;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender emailSender;
    @Value("${spring.mail.username}") private String sender;

    public EmailService(JavaMailSender emailSender) {

        this.emailSender = emailSender;
    }

    public void sendSimpleEmail(Email email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(email.getRecipient());
        message.setTo(email.getMsgBody());
        message.setSubject(email.getSubject());

        emailSender.send(message);
    }
}
