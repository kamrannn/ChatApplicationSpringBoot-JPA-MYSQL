package com.chatapplicationspringBoot.service;

import com.chatapplicationspringBoot.model.entity.Category;
import com.chatapplicationspringBoot.model.entity.Chat;
import com.chatapplicationspringBoot.model.entity.User;
import com.chatapplicationspringBoot.model.interfaces.thirdpartyDTO.UserChatsAndCategories;
import com.chatapplicationspringBoot.model.interfaces.databaseDTO.UserChatAndCategoriesDB;
import com.chatapplicationspringBoot.repository.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
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

@Service
public class UserService {
    private final String ACCOUNT_SID ="AC899fa2ea88ed71b93e716ffb0135a969";
    private final String AUTH_TOKEN = "c185984f543eb96f17b65638b8393f99";
    private final String FROM_NUMBER = "+17242515324";

    private static final Logger LOG = LogManager.getLogger(UserService.class);
    HttpHeaders httpHeaders = new HttpHeaders();
    final String baseUrl = "http://192.168.10.8:8080/user/";
    URI uri;
    // Autowired, Constructor is made
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @param email "taking email from the user"
     * @param password "taking password from the user"
     * @return
     * @Author "Kamran"
     * @Description "Authenticating the user with email and password"
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
     * @param user
     * @Author "Kamran"
     * @Description "Save User into database by getting values from controller"
     */
    public ResponseEntity<Object> saveUser(User user) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = formatter.format(new Date());
        int size = user.getChats().size();
        for (int i = 0; i < size; i++) {
            user.getChats().get(i).setCreateDate(date); //setting chats creation date
        }
        user.setStatus(true); //the user is active in the start
        user.setCreateDate(date); //setting the user creation date
        try {
            userRepository.save(user); //saving the user in the database
            return new ResponseEntity<>("User has been successfully Added", HttpStatus.OK);
        } catch (Exception e) {
            LOG.info("Exception: " + e.getMessage());
            return new ResponseEntity<>("The user already exists", HttpStatus.CONFLICT);
        }
    }

    /**
     * @param id
     * @return
     * @Author "Kamran"
     * @Description "Finding the User from database using userID"
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

    //Delete user from db by using user ID
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
     * @param user
     * @return
     * @Author "Kamran"
     * @Description "Updating the user with the user Object"
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
     * @param userId
     * @param chat
     * @return
     * @Author "Kamran"
     * @Description "Adding the Chat with respect to User ID"
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
     * @param userId
     * @return
     * @Author "Kamran"
     * @Description "Getting the chat and categories list of a particular user
     from our database if available else checking from 3rd party Rest API, if that user don't exist
    in both of them then we will return a message of not having that user's chat and categories."
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

    public ResponseEntity<Object> SendSms(long id, String userMessage){

        User user = userRepository.getById(id);
        if(null==user){
            return new ResponseEntity<>("There is no user exists against this id",HttpStatus.BAD_REQUEST);
        }
        else{
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(new PhoneNumber("+923125153352"), new PhoneNumber(FROM_NUMBER), userMessage)
                    .create();
            System.out.println("here is my id:"+message.getSid());// Unique resource ID created to manage this transaction
        }
        return new ResponseEntity<>("",HttpStatus.OK);
    }
}
