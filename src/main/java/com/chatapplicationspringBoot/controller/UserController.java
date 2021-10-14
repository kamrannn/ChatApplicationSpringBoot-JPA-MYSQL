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

@EnableSwagger2
@RestController
@RequestMapping("/users")
@Api(value="User Operations - CRUD REST API's for the User")
public class UserController {
    private static final Logger LOG =  LogManager.getLogger(UserController.class);
    //This is token for checking authorization
    private static final String token = "40dc498b-e837-4fa9-8e53-c1d51e01af15";

    final UserService userService;
    //UserService constructor, used in place of Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @Author "Kamran"
     * @Description "Login it takes email and password from frontend then check from database by calling object with email"
     * @param email
     * @param password
     * @return
     */
    @GetMapping("/login")
    public ResponseEntity<Object> UserLogin(@RequestHeader String email , @RequestHeader String password) {
        return userService.Authentication(email,password);
    }

    /**
     * @Author "Kamran"
     * @description "Authorizing the token"
     * @param token
     * @return
     */
    public boolean authorization(String token) {
        LOG.info("Authorizing the user ");
        return UserController.token.equals(token);
    }

    /**
     * @Author "Kamran"
     * @Description "if the user is un-authorized"
     * @return
     */
    public ResponseEntity<Object> UnAuthorizeUser() {
        LOG.info("Unauthorized user is trying to get access");
        return new ResponseEntity<>("Kindly do the authorization first", HttpStatus.UNAUTHORIZED);
    }

    /**
     * @Author "Kamran"
     * @Description "This api is listing all the users present in the database"
     * @param token
     * @return
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
     * @Author "Kamran"
     * @Description "This API just add the user"
     * @param token
     * @param user
     * @return
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
     * @Author "Kamran"
     * @Description "This API only show certain object by taking on ID number"
     * @param token
     * @param id
     * @return
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
     * @Author "Kamran"
     * @Description " This API updates the user by just giving certain ID all values should be updated otherwise other fields will be NULL"
     * @param token
     * @param user
     * @return
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
     * @Author "Kamran"
     * @Description " This API deletes the user by using Path variable"
     * @param token
     * @param id
     * @return
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
     * @Author "Kamran"
     * @Description " This API deletes the user by using Request Parameter"
     * @param token
     * @param id
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
     * @Author "Kamran"
     * @Description " This API is adding the chat with respect to User ID"
     * @param userId
     * @param chat
     * @return
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
     * @Author "Kamran"
     * @param id
     * @param message
     * @param token
     * @return
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
     * @Author "Kamran"
     * @Description "This method is sending email to specific emails"
     * @CreatedDate "14-10-2021
     * @param token
     * @param id
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
     * @Author "Kamran"
     * @Description "This method is using to verify the user account"
     * @CreatedDate "14-10-2021"
     * @param id
     * @param smsToken
     * @param emailToken
     * @return
     */
    @GetMapping("/verification")
    public ResponseEntity<Object> AccountVerification(@RequestHeader Long id, @RequestHeader String smsToken, @RequestHeader String emailToken){
        return userService.AccountVerification(id,smsToken,emailToken);
    }
}
