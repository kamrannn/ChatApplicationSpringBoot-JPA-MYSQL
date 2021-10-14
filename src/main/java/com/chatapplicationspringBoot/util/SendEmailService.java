package com.chatapplicationspringBoot.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class SendEmailService {

    private final JavaMailSender javaMailSender;
    public SendEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String email, String emailToken) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email, email);
        msg.setSubject("Account Verification Token");
        msg.setText(emailToken);
        javaMailSender.send(msg);
    }
}
