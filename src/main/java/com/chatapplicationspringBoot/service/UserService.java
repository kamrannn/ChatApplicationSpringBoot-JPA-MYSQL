package com.chatapplicationspringBoot.service;

import com.chatapplicationspringBoot.model.entity.Category;
import com.chatapplicationspringBoot.model.entity.Chat;
import com.chatapplicationspringBoot.model.entity.User;
import com.chatapplicationspringBoot.model.interfaces.thirdpartyDTO.UserChatsAndCategories;
import com.chatapplicationspringBoot.model.interfaces.databaseDTO.UserChatAndCategoriesDB;
import com.chatapplicationspringBoot.repository.UserRepository;
import com.chatapplicationspringBoot.util.SmsUtility;
import com.chatapplicationspringBoot.util.SendEmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * The type User service.
 */
@Service
public class UserService {
    /**
     * LOGGER
     */
    private static final Logger LOG = LogManager.getLogger(UserService.class);
    /**
     * The Http headers.
     */
    HttpHeaders httpHeaders = new HttpHeaders();
    /**
     * The Base url of the target API.
     */
    final String baseUrl = "http://192.168.100.63:8080/user/";
    /**
     * The Uri.
     */
    URI uri;
    /**
     * Instantiating the user repository
     */
    private final UserRepository userRepository;
    /**
     * The email service utility.
     */
    SendEmailService sendEmailService;
    /**
     * The Sms utility.
     */
    SmsUtility smsUtility;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository   the user repository
     * @param sendEmailService sending email service
     * @param smsUtility       the sms utility
     */
    public UserService(UserRepository userRepository, SendEmailService sendEmailService, SmsUtility smsUtility) {
        this.userRepository = userRepository;
        this.sendEmailService = sendEmailService;
        this.smsUtility = smsUtility;
    }

    /**
     * Authenticating the user with email and password.
     *
     * @param email    "taking email from the user"
     * @param password "taking password from the user"
     * @return response entity
     * @Author "Kamran"
     */
    public ResponseEntity<Object> Authentication(String email, String password) {
        try {
            Optional<User> user = userRepository.findUserByEmailAndPassword(email, password);
            if (user.isPresent()) {
                return new ResponseEntity<>("You are successfully Logged in", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("You are entering wrong email or Password", HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            LOG.info("Exception: " + exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * List all users response entity.
     *
     * @return "all users from Database"
     * @Author "Kamran"
     */
    public ResponseEntity<Object> listAllUsers() {
        try {
            List<User> userList = userRepository.findAllByStatus(true);
            if (userList.isEmpty()) {
                return new ResponseEntity<>("No User exists in the database", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(userList, HttpStatus.OK); //if data found
            }
        } catch (Exception exception) {
            LOG.info("Exception: " + exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Save User into database by getting values from the frontend.
     *
     * @param user the user
     * @return the response entity
     * @Author "Kamran"
     */
    public ResponseEntity<Object> saveUser(User user) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = formatter.format(new Date());
        int size = user.getChats().size();
        for (int i = 0; i < size; i++) {
            user.getChats().get(i).setCreateDate(date); //setting chats creation date
        }

        try {
            Random rnd = new Random(); //Generating a random number
            int emailToken = rnd.nextInt(999999) + 100000; //Generating a random number of 6 digits
            //Using the email service utility to send the email to the entered email
            sendEmailService.sendMail(user.getEmail(), "Your verification code is: " + emailToken);
            //Generating SMS token for the user
            int smsToken = rnd.nextInt(999999) + 100000;
            //Using the sms utility to send the message to the entered phone number
            smsUtility.Notification(user.getPhoneNo(), "Your verification code: " + smsToken);
            //setting the tokens in database
            user.setEmailToken(emailToken + ""); //setting the email generated token in database
            user.setSmsToken(smsToken + ""); //setting the sms generated token in database
            user.setCreateDate(date); //setting the user creation date
            user.setStatus(false); //the user is active in the start
            userRepository.save(user); //saving the user in the database
            return new ResponseEntity<>("User has been successfully Added", HttpStatus.OK);
        } catch (Exception e) {
            LOG.info("Exception: " + e.getMessage());
            return new ResponseEntity<>("The user already exists", HttpStatus.CONFLICT);
        }
    }

    /**
     * Finding the User from database using userID
     *
     * @param id the id
     * @return user
     * @Author "Kamran"
     */
    public ResponseEntity<Object> getUser(Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                return new ResponseEntity<>(user, HttpStatus.FOUND);
            } else {
                return new ResponseEntity<>("User not found ", HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete user from db by using user ID.
     *
     * @param id the id
     * @return the response entity
     */
    public ResponseEntity<Object> deleteUser(Long id) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = formatter.format(new Date());
            Optional<User> user = userRepository.findUsersById(id);
            user.get().setUpdateDate(date);
            user.get().setStatus(false);
            userRepository.deleteById(id);
            return new ResponseEntity<>("User is successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            LOG.info("Exception: " + e.getMessage());
            return new ResponseEntity<>("This user doesn't exist in the database", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updating the user with the user Object.
     *
     * @param user the user
     * @return response entity
     * @Author "Kamran"
     */
    public ResponseEntity<Object> updateUser(User user) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = formatter.format(new Date());
            user.setUpdateDate(date);
            userRepository.save(user);
            return new ResponseEntity<>("User has been successfully Updated", HttpStatus.OK);
        } catch (Exception e) {
            LOG.info("Exception: " + e.getMessage());
            return new ResponseEntity<>("User is not Updated", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Adding the Chat with respect to User ID.
     *
     * @param userId the user id
     * @param chat   the chat
     * @return response entity
     * @Author "Kamran"
     */
    public ResponseEntity<Object> AddChatByUserID(long userId, List<Chat> chat) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            int chatListSize = chat.size();
            for (int i = 0; i < chatListSize; i++) {
                String date = formatter.format(new Date());
                chat.get(i).setCreateDate(date);
            }
            Optional<User> user = userRepository.findUsersById(userId);//Getting the user object
            if (user.isPresent()) {
                List<Chat> userChats = user.get().getChats();
                try {
                    for (Chat newChat : chat) {
                        userChats.add(newChat);
                    }
                    userRepository.save(user.get());
                    return new ResponseEntity<>("Chat has been successfully Added", HttpStatus.OK);
                } catch (Exception e) {
                    LOG.info("Exception: " + e.getMessage());
                    return new ResponseEntity<>("Chat is not Added", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("User not found against this user ID", HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            LOG.info("Exception: " + exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Getting the chat and categories list of a particular user from our database if available else checking
     * from 3rd party Rest API, if that user don't exist in both of them then we will
     * return a message of not having that user's chat and categories.
     *
     * @param userId the user id
     * @return response entity
     * @Author "Kamran"
     */
    public ResponseEntity<Object> GetChatAndCategories(long userId) {
        UserChatAndCategoriesDB userDatabaseDTO = new UserChatAndCategoriesDB(); //Using this object to store the list from our own database
        try {
            Optional<User> user = userRepository.findUsersById(userId);//finding the user in the database with ID
            if (user.isPresent()) { //checking if the user is present or not
                List<Chat> userChats = user.get().getChats(); //getting that specific user chat
                userDatabaseDTO.setChatList(userChats);
                List<Category> userCategoriesList = user.get().getCategories(); //getting that specific user categories
                userDatabaseDTO.setCategoryList(userCategoriesList);
                //if there is no data (both should be empty) in the users chats and categories list
                if (userChats.isEmpty() && userCategoriesList.isEmpty()) {
                    return new ResponseEntity<>("There are no chats and categories against this user", HttpStatus.NOT_FOUND);
                } else { //it will run even if one of the list is empty
                    return new ResponseEntity<>(userDatabaseDTO, HttpStatus.FOUND); //if data found for the user
                }
            } else { //If that user is not in our Database, checking 3rd party API through rest template
                uri = new URI(baseUrl + userId); //url with user it, concatination is done with user id
                LOG.info(uri);
                httpHeaders.set("Authorization", "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454"); //Authorization in the header
                HttpEntity<Object> requestEntity = new HttpEntity<>(null, httpHeaders);
                LOG.info(requestEntity);
                UserChatsAndCategories userChatsAndCategories = new UserChatsAndCategories(); //this is
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<com.chatapplicationspringBoot.model.interfaces.thirdpartyDTO.UserDTO> userDTOResponseEntity =
                        restTemplate.exchange(uri, HttpMethod.GET, requestEntity, com.chatapplicationspringBoot.model.interfaces.thirdpartyDTO.UserDTO.class);

                userChatsAndCategories.setUserCategoriesList(userDTOResponseEntity.getBody().getCategoryList());
                userChatsAndCategories.setUserChatList(userDTOResponseEntity.getBody().getChatList());

                return new ResponseEntity<>(userChatsAndCategories, HttpStatus.FOUND);
            }
        } catch (Exception exception) {
            LOG.info("Exception: " + exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * using this method to send sms to the specific user
     *
     * @param id                  the id
     * @param notificationMessage the notification message
     * @return response entity
     * @Author "Kamran"
     * @CreatedDate "10-13-2021"
     */
    public ResponseEntity<Object> SendSms(long id, String notificationMessage) {
        try {
            Optional<User> user = userRepository.findUsersById(id);
            if (user.isPresent()) {
                return smsUtility.Notification(user.get().getPhoneNo(), notificationMessage);
            } else {
                uri = new URI(baseUrl + id); //url with user it, concatination is done with user id
                LOG.info(uri);
                httpHeaders.set("Authorization", "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454"); //Authorization in the header
                HttpEntity<Object> requestEntity = new HttpEntity<>(null, httpHeaders);
                LOG.info(requestEntity);
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<com.chatapplicationspringBoot.model.interfaces.thirdpartyDTO.UserDTO> userDTOResponseEntity =
                        restTemplate.exchange(uri, HttpMethod.GET, requestEntity, com.chatapplicationspringBoot.model.interfaces.thirdpartyDTO.UserDTO.class);
                return smsUtility.Notification(userDTOResponseEntity.getBody().getContactNum(), notificationMessage);
            }
        } catch (Exception e) {
            LOG.info("Exception: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This function is checking the email service utility.
     *
     * @param email the email
     * @return the response entity
     * @Author "Kamran"
     */
    public ResponseEntity<Object> sendEmail(String email) {
        try{
            System.out.println(email);
            sendEmailService.sendMail(email, "Checking Email API");
            return new ResponseEntity<>("Email has been sent",HttpStatus.OK);
        }catch (Exception e){
            LOG.info(e.getMessage());
            return new ResponseEntity<>("Email is not sent",HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method is doing account verification by comparing user entered and database saved sms token and email token.
     * @Author "Kamran"
     * @CreatedDate "14-10-2021
     * @param id         the id
     * @param smsToken   the sms token
     * @param emailToken the email token
     * @return response entity
     */
    public ResponseEntity<Object> AccountVerification(long id, String smsToken, String emailToken) {
        try {
            Optional<User> user = userRepository.findByIdAndSmsTokenAndEmailToken(id, smsToken, emailToken);
            if (user.isPresent()) {
                user.get().setStatus(true);
                userRepository.save(user.get());
                return new ResponseEntity<>("User account has been verified", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Your are entering wrong credentials", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
