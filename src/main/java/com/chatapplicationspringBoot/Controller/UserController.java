package com.chatapplicationspringBoot.Controller;

import com.chatapplicationspringBoot.Model.Chat;
import com.chatapplicationspringBoot.Model.User;
import com.chatapplicationspringBoot.Service.UserService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@EnableSwagger2
@RestController
@RequestMapping("/users")
@Api(value="User Operations - CRUD REST API's for the User")
public class UserController {
    final UserService userService;
    //UserService constructor, used in place of Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    //This is token for checking authorization
    private static final String token = "40dc498b-e837-4fa9-8e53-c1d51e01af15";

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
     * @Description "Authorizing the token"
     * @param token
     * @return
     */
    public boolean authorization(String token) {
        return UserController.token.equals(token);
    }

    /**
     * @Author "Kamran"
     * @Description "if the user is un-authorized"
     * @return
     */
    public ResponseEntity<Object> UnAuthorizeUser() {
        return new ResponseEntity<>("Kindly login first", HttpStatus.UNAUTHORIZED);
    }

    /**
     * @Author "Kamran"
     * @Description "This api is listing all the users present in the database"
     * @param token
     * @return
     */
    @GetMapping("/listofusers")
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
    @PostMapping("/adduser")
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
     * @param key1
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserByID(@RequestHeader("Authorization") String key1, @PathVariable Long id) {
            if (authorization(key1)) {
                return userService.getUser(id); //It will return the Response
            } else {
                return UnAuthorizeUser(); //If the user is not authorized
            }
    }

    /**
     * @Author "Kamran"
     * @Description " This API updates the user by just giving certain ID all values should be updated otherwise other fields will be NULL"
     * @param key1
     * @param user
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestHeader("Authorization") String key1, @RequestBody User user) {
        if (authorization(key1)) {
            try {
                return userService.updateUser(user);
            } catch (Exception exception) {
                return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
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
    public ResponseEntity<Object> delete(@RequestHeader("Authorization") String token, @RequestParam("delete") Long id) {
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
     * @Description " This API is adding the chat with respect to User ID"
     * @param userID
     * @param chat
     * @return
     */
    @PostMapping("/add/chat")
    public ResponseEntity<Object> AddChatByUserID(@RequestHeader long userID, @RequestBody List<Chat> chat) {
        try {
            return userService.AddChatByUserID(userID, chat);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
