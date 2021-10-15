package com.chatapplicationspringBoot.controller;

import com.chatapplicationspringBoot.model.entity.Chat;
import com.chatapplicationspringBoot.service.ChatService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.List;

/**
 * The type Chat controller.
 */
@EnableSwagger2
@RestController
@RequestMapping("/chats")
public class ChatController {
    private static final Logger LOG =  LogManager.getLogger(ChatController.class);

    //Autowiring through constructor
    private final ChatService chatService;

    /**
     * Instantiates a new Chat controller.
     *
     * @param chatService the chat service
     */
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * @Author "Kamran"
     * @Description "key value for the authorization of the "
     */
    private final static String token = "40dc498b-e837-4fa9-8e53-c1d51e01af15";

    /**
     * Authorization boolean.
     *
     * @param userToken the user token
     * @return boolean
     * @Author "Kamran"
     * @Description "This method is authorizing the user to get access to the API"
     */
    public boolean authorization(String userToken) {
        LOG.info("User is successfully authorized");
        return token.equals(userToken);
    }

    /**
     * Un authorize user response entity.
     *
     * @return "Response if the user is not authorized"
     * @Author "Kamran"
     * @description "We are having this method to return the un authorize user.
     */
    public ResponseEntity<Object> UnAuthorizeUser() {
        LOG.info("Unauthorized user is trying to get access");
        return new ResponseEntity<>("Kindly do the authorization first", HttpStatus.UNAUTHORIZED);
    }

    /**
     * List response entity.
     *
     * @param token the token
     * @return response entity
     * @Author "Kamran"
     * @Description This API shows all the chats
     */
    @GetMapping("")
    public ResponseEntity<Object> list(@RequestHeader("Authorization") String token) {
        if (authorization(token)) {
            return chatService.ListAllChat();
        } else {
            return UnAuthorizeUser();
        }
    }

    /**
     * Get question response entity.
     *
     * @param token the token
     * @param id    the id
     * @return response entity
     * @Author "Kamran"
     * @Description "This API only shows the specific question by taking Question ID as a Path variable"
     */
    @GetMapping("/chat/{id}")
    public ResponseEntity<Object> GetQuestion(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        if (authorization(token)) {
            return chatService.getChat(id);
        }else {
            return UnAuthorizeUser();
        }
    }

    /**
     * Get by id response entity.
     *
     * @param token the token
     * @param id    the id
     * @return response entity
     * @Author "Kamran"
     * @Description "This API only show specific question by taking question ID in Request parameter"
     */
    @GetMapping("/chat")
    public ResponseEntity<Object> GetById(@RequestHeader("Authorization") String token,@RequestParam("chatId") Long id){
        if (authorization(token)) {
            return chatService.getChat(id);
        }else {
            return UnAuthorizeUser();
        }
    }

    /**
     * Update chat response entity.
     *
     * @param token the token
     * @param chats the chats
     * @return response entity
     * @author Kamran Abbasi
     * @description This API updates the chat by passing the Chat object
     */
    @PutMapping("/update")
    public ResponseEntity<Object> UpdateChat(@RequestHeader("Authorization") String token, @RequestBody List<Chat> chats) {
        if(authorization(token)){
            return chatService.updateChat(chats);
        }
        else {
            return UnAuthorizeUser();
        }
    }

    /**
     * Delete response entity.
     *
     * @param id    the id
     * @param token the token
     * @return the response entity
     * @author "Kamran"
     * @description "This API deletes specific chat using Question ID in Path variable"
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete( @PathVariable Long id,@RequestHeader("Authorization") String token) {
        if (authorization(token)) {
            return chatService.deleteChat(id);
        }
        else{
            return UnAuthorizeUser();
        }
    }

    /**
     * Delete response entity.
     *
     * @param token the token
     * @param id    the id
     * @return the response entity
     * @Author "Kamran"
     * @Description "This API deletes certain chat using request parameter"
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestHeader("Authorization") String token,@RequestParam ("delete") Long id) {
        if (authorization(token)) {
            return chatService.deleteChat(id);
        }
        else{
            return UnAuthorizeUser();
        }
    }
}
