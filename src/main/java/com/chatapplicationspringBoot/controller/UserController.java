package com.chatapplicationspringBoot.controller;

import com.chatapplicationspringBoot.model.entity.Chat;
import com.chatapplicationspringBoot.model.entity.User;
import com.chatapplicationspringBoot.service.UserService;
import io.swagger.annotations.Api;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.List;

/**
 * The type User controller.
 */
@EnableSwagger2
@RestController
@RequestMapping("/users")
@Api(value="User Operations - CRUD REST API's for the User")
public class UserController {
    private static final Logger LOG =  LogManager.getLogger(UserController.class);
    //This is token for checking authorization
    private static final String token = "40dc498b-e837-4fa9-8e53-c1d51e01af15";

    /**
     * The User service.
     */
    final UserService userService;

    /**
     * Instantiates a new User controller.
     *
     * @param userService the user service
     */
//UserService constructor, used in place of Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * User login response entity.
     *
     * @param email    the email
     * @param password the password
     * @return response entity
     * @Author "Kamran"
     * @Description "Login it takes email and password from frontend then check from database by calling object with email"
     */
    @GetMapping("/login")
    public ResponseEntity<Object> UserLogin(@RequestHeader String email , @RequestHeader String password) {
        return userService.Authentication(email,password);
    }

    /**
     * Authorization boolean.
     *
     * @param token the token
     * @return boolean
     * @Author "Kamran"
     * @description "Authorizing the token"
     */
    public boolean authorization(String token) {
        LOG.info("Authorizing the user ");
        return UserController.token.equals(token);
    }

    /**
     * Un authorize user response entity.
     *
     * @return response entity
     * @Author "Kamran"
     * @Description "if the user is un-authorized"
     */
    public ResponseEntity<Object> UnAuthorizeUser() {
        LOG.info("Unauthorized user is trying to get access");
        return new ResponseEntity<>("Kindly do the authorization first", HttpStatus.UNAUTHORIZED);
    }

    /**
     * List of users response entity.
     *
     * @param token the token
     * @return response entity
     * @Author "Kamran"
     * @Description "This api is listing all the users present in the database"
     */
    @GetMapping("/list")
    public ResponseEntity<Object> listOfUsers(@RequestHeader("Authorization") String token) {
            if (authorization(token)) {
                return userService.listAllUsers();
            } else {
                return UnAuthorizeUser();
            }
    }

    /**
     * Add response entity.
     *
     * @param token the token
     * @param user  the user
     * @return response entity
     * @Author "Kamran"
     * @Description "This API just add the user"
     */
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestHeader("Authorization") String token, @RequestBody User user) {
        try{
            if (authorization(token)) {
                return userService.saveUser(user);
            } else {
                return UnAuthorizeUser();
            }
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets user by id.
     *
     * @param token the token
     * @param id    the id
     * @return user by id
     * @Author "Kamran"
     * @Description "This API only show certain object by taking on ID number"
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserByID(@RequestHeader("Authorization") String token, @PathVariable Long id) {
            if (authorization(token)) {
                return userService.getUser(id); //It will return the Response
            } else {
                return UnAuthorizeUser(); //If the user is not authorized
            }
    }

    /**
     * Update user response entity.
     *
     * @param token the token
     * @param user  the user
     * @return response entity
     * @Author "Kamran"
     * @Description " This API updates the user by just giving certain ID all values should be updated otherwise other fields will be NULL"
     */
    @PutMapping("/update")
    public ResponseEntity<Object> UpdateUser(@RequestHeader("Authorization") String token, @RequestBody User user) {
        if (authorization(token)) {
            return userService.updateUser(user);
        } else {
            return UnAuthorizeUser() ;
        }
    }

    /**
     * Delete user response entity.
     *
     * @param id    the id
     * @param token the token
     * @return response entity
     * @Author "Kamran"
     * @Description " This API deletes the user by using Path variable"
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> DeleteUser(@PathVariable Long id, @RequestHeader("Authorization") String token) {

        if (authorization(token)) {
            try{
                return userService.deleteUser(id);
            }catch (Exception exception){
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else{
            return UnAuthorizeUser();
        }
    }

    /**
     * Delete user response entity.
     *
     * @param token the token
     * @param id    the id
     * @return the response entity
     * @Author "Kamran"
     * @Description " This API deletes the user by using Request Parameter"
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> DeleteUser(@RequestHeader("Authorization") String token, @RequestParam("delete") Long id) {
        if (authorization(token)) {
            try{
                return userService.deleteUser(id);
            }catch (Exception exception){
                LOG.info("Exception: "+exception.getMessage());
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else{
            return UnAuthorizeUser();
        }
    }


    /**
     * Add chat by user id response entity.
     *
     * @param userId the user id
     * @param chat   the chat
     * @return response entity
     * @Author "Kamran"
     * @Description " This API is adding the chat with respect to User ID"
     */
    @PostMapping("/add/chat")
    public ResponseEntity<Object> AddChatByUserID(@RequestHeader long userId, @RequestBody List<Chat> chat) {
        try {
            return userService.AddChatByUserID(userId, chat);
        }catch (Exception exception){
            LOG.info("Exception: "+exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Chat of single user response entity.
     *
     * @param userId the user id
     * @return the response entity
     */
    @GetMapping("/user/details")
    public ResponseEntity<Object> ChatOfSingleUser(@RequestParam("userId") long userId) {
        try {
            return userService.GetChatAndCategories(userId);
        }catch (Exception exception){
            LOG.info("Exception: "+exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Send sms response entity.
     *
     * @param token   the token
     * @param id      the id
     * @param message the message
     * @return response entity
     * @Author "Kamran"
     */
    @PostMapping("/sms")
    public ResponseEntity<Object> SendSms(@RequestHeader("Authorization") String token,@RequestHeader long id, @RequestBody String message) {

        if(authorization(token)){
            return userService.SendSms(id, message);
        }
        else{
            return UnAuthorizeUser();
        }
    }

    /**
     * Send email response entity.
     *
     * @param token the token
     * @param id    the id
     * @return the response entity
     * @Author "Kamran"
     * @Description "This method is sending email to specific emails"
     * @CreatedDate "14-10-2021
     */
    @PostMapping("/email")
    public ResponseEntity<Object> SendEmail(@RequestHeader("Authorization") String token, @RequestHeader long id) {
        if (authorization(token)){
            return userService.sendEmail("rajakamran737@gmail.com");
        }
        else {
            return UnAuthorizeUser();
        }
    }

    /**
     * Account verification response entity.
     *
     * @param id         the id
     * @param smsToken   the sms token
     * @param emailToken the email token
     * @return response entity
     * @Author "Kamran"
     * @Description "This method is using to verify the user account"
     * @CreatedDate "14-10-2021"
     */
    @GetMapping("/verification")
    public ResponseEntity<Object> AccountVerification(@RequestHeader Long id, @RequestHeader String smsToken, @RequestHeader String emailToken){
        return userService.AccountVerification(id,smsToken,emailToken);
    }
}
