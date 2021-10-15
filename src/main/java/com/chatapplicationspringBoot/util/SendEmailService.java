package com.chatapplicationspringBoot.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * This class is providing the service to send message to the specific number.
 *
 * @Author "Kamran"
 * @CreatedDate "14-10-2021
 */
@Service
public class SendEmailService {

    private final JavaMailSender javaMailSender;

    /**
     * Instantiates a new Send email service.
     *
     * @param javaMailSender the java mail sender
     */
    public SendEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * This method is using accepting email ID and message to send mail through JavaMailSender(SMTP).
     *
     * @param email   the email
     * @param message the message
     * @Author "Kamran"
     * @CreatedDate "14-10-2021
     */
    public void sendMail(String email, String message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email, email);
        msg.setSubject("Account Verification Token");
        msg.setText(message);
        javaMailSender.send(msg);
    }
}
