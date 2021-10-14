package com.chatapplicationspringBoot.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @Author "Kamran"
 * @Description "This class is providing the service to send message to the specific number
 * @CreatedDate "14-10-2021
 */
@Service
public class SendEmailService {

    private final JavaMailSender javaMailSender;
    public SendEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * @Author "Kamran"
     * @Description "This method is using accepting email ID and message to send mail through JavaMailSender(SMTP)
     * @CreatedDate "14-10-2021
     * @param email
     * @param message
     */
    public void sendMail(String email, String message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email, email);
        msg.setSubject("Account Verification Token");
        msg.setText(message);
        javaMailSender.send(msg);
    }
}
