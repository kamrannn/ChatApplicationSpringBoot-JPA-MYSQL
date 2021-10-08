package com.chatapplicationspringBoot.service;

import com.chatapplicationspringBoot.model.entity.Category;
import com.chatapplicationspringBoot.model.entity.Chat;
import com.chatapplicationspringBoot.model.entity.User;
import com.chatapplicationspringBoot.model.interfaces.UserChatsAndCategories;
import com.chatapplicationspringBoot.model.interfaces.UserDbDTO;
import com.chatapplicationspringBoot.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger LOG =  LogManager.getLogger(UserService.class);
    // Autowired, Constructor is made
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @Author "Kamran"
     * @Description "Authenticating the user with email and password"
     * @param email
     * @param password
     * @return
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
            LOG.info("Exception: "+exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @Author "Kamran"
     * @return "all users from Database"
     */
    public ResponseEntity<Object> listAllUsers() {
        try {
            List<User> userList =  userRepository.findAll();
                if(userList.isEmpty()){
                    return new ResponseEntity<>("No User exists in the database", HttpStatus.NOT_FOUND);
                }
                else{
                    return new ResponseEntity<>(userList, HttpStatus.OK); //if data found
                }
        } catch (Exception exception) {
            LOG.info("Exception: "+exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @Author "Kamran"
     * @Description "Save User into database by getting values from controller"
     * @param user
     */
    public ResponseEntity<Object> saveUser(User user) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        int size =  user.getChats().size();
        for(int i=0;i<size;i++){
            String date = formatter.format(new Date());
            user.getChats().get(i).setQuestionDate(date);
            user.getChats().get(i).setAnswerDate(date);
        }
        try {
            userRepository.save(user);
            return new ResponseEntity<>("User has been successfully Added", HttpStatus.OK);
        } catch (Exception e) {
            LOG.info("Exception: "+e.getMessage());
            return new ResponseEntity<>("The user already exists", HttpStatus.CONFLICT);
        }
    }

    /**
     * @Author "Kamran"
     * @Description "Finding the User from database using userID"
     * @param id
     * @return
     */
    public ResponseEntity<Object> getUser(Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()){
                return new ResponseEntity<>(user,HttpStatus.FOUND);
            }
            else{
                return new ResponseEntity<>("User not found ",HttpStatus.NOT_FOUND);
            }
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Delete user from db by using user ID
    public ResponseEntity<Object> deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>("User is successfully deleted", HttpStatus.OK);
        }catch (Exception e){
            LOG.info("Exception: "+e.getMessage());
            return new ResponseEntity<>("This user doesn't exist in the database", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Author "Kamran"
     * @Description "Updating the user with the user Object"
     * @param user
     * @return
     */
    public ResponseEntity<Object> updateUser(User user) {
        try {
            userRepository.save(user);
            return new ResponseEntity<>("User has been successfully Updated", HttpStatus.OK);
        } catch (Exception e) {
            LOG.info("Exception: "+e.getMessage());
            return new ResponseEntity<>("User is not Updated", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Author "Kamran"
     * @Description "Adding the Chat with respect to User ID"
     * @param userId
     * @param chat
     * @return
     */
    public ResponseEntity<Object> AddChatByUserID(long userId, List<Chat> chat) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try{
            int chatListSize =  chat.size();
            for(int i=0;i<chatListSize;i++){
                String date = formatter.format(new Date());
                chat.get(i).setQuestionDate(date);
                chat.get(i).setAnswerDate(date);
            }
            Optional<User> user = userRepository.findUsersById(userId);//Getting the user object
            if(user.isPresent()){
                List<Chat> userChats = user.get().getChats();
                try{
                    for (Chat newChat:chat) {
                        userChats.add(newChat);
                    }
                    userRepository.save(user.get());
                    return new ResponseEntity<>("Chat has been successfully Added", HttpStatus.OK);
                }catch (Exception e){
                    LOG.info("Exception: "+e.getMessage());
                    return new ResponseEntity<>("Chat is not Added", HttpStatus.BAD_REQUEST);
                }
            }
            else{
                return new ResponseEntity<>("User not found against this user ID", HttpStatus.NOT_FOUND);
            }
        }catch (Exception exception){
            LOG.info("Exception: "+exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> ChatOfSingleUser(long userId) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        UserDbDTO userDbDTO = new UserDbDTO() ;

        try{
            Optional<User> user = userRepository.findUsersById(userId);//Getting the user object
            if(user.isPresent()){
                List<Chat> userChats = user.get().getChats();
                userDbDTO.setChatList(userChats);
                List<Category> userCategoriesList = user.get().getCategories();
                userDbDTO.setCategoryList(userCategoriesList);

                if(userChats.isEmpty()&&userCategoriesList.isEmpty()){
                    return new ResponseEntity<>("There are no chats and categories against this user", HttpStatus.NOT_FOUND);
                }
                else{
                    return new ResponseEntity<>(userDbDTO, HttpStatus.FOUND);
                }
            }
            else{
                return new ResponseEntity<>("User not found against this user ID", HttpStatus.NOT_FOUND);
            }
        }catch (Exception exception){
            LOG.info("Exception: "+exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
