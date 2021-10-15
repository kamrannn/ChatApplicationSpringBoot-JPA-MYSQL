package com.chatapplicationspringBoot.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * The Sms utility class that is providing sms service to phone numbers.
 */
@Service
public class SmsUtility {
    private final String ACCOUNT_SID ="AC899fa2ea88ed71b93e716ffb0135a969"; //twilio account SID
    private final String AUTH_TOKEN = "d946fadd211be139e44953ce69de6232"; //twilio account TOKEN
    private final String FROM_NUMBER = "+17242515324"; //twilio TRIAL Number

    /**
     * Notification response entity to send email to specific email.
     *
     * @param toNumber    the to number
     * @param userMessage the user message
     * @return response entity
     * @Author "Kamran"
     * @Description "This method is using accepting phone number and message to send message through Twilio
     * @CreatedDate "14-10-2021
     */
    public ResponseEntity<Object> Notification(String toNumber, String userMessage){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber(toNumber), new PhoneNumber(FROM_NUMBER), userMessage)
                .create();
        System.out.println("here is my id:"+message.getSid());// Unique resource ID created to manage this transaction
        return new ResponseEntity<>("The message has been successfully sent to: "+toNumber, HttpStatus.OK);
    }
}
