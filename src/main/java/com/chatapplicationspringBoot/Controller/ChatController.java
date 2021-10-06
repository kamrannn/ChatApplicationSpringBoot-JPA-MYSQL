package com.chatapplicationspringBoot.Controller;

import com.chatapplicationspringBoot.Model.Chat;
import com.chatapplicationspringBoot.Service.ChatService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.NoSuchElementException;

@EnableSwagger2
@RestController
@RequestMapping("/chats")
public class ChatController {
    private static final Logger LOG =  LogManager.getLogger(ChatController.class);

    //Autowiring through constructor
    private final ChatService chatService;
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    //key value for the authorization
    private final static String token = "40dc498b-e837-4fa9-8e53-c1d51e01af15";

    public boolean authorization(String userToken) {
        LOG.info("User is successfully authorized");
        return token.equals(userToken);
    }

    public ResponseEntity<Object> UnAuthorizeUser() {
        LOG.info("Unauthorized user is trying to get access");
        return new ResponseEntity<>("Kindly login first", HttpStatus.UNAUTHORIZED);
    }

    /**
     * @Author "Kamran"
     * @Description This API shows all the chats
     * @param token
     * @return
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
     * @Author "Kamran"
     * @Description "This API only shows the specific question by taking Question ID as a Path variable"
     * @param token
     * @param id
     * @return
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
     * @Author "Kamran"
     * @Description "This API only show specific question by taking question ID in Request parameter"
     * @param token
     * @param id
     * @return
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
     * @author Kamran Abbasi
     * @description This API updates the chat by passing the Chat object
     * @param chats
     * @return
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
     * @author "Kamran"
     * @description "This API deletes specific chat using Question ID in Path variable"
     * @param id
     * @param token
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
     * @Author "Kamran"
     * @Description "This API deletes certain chat using request parameter"
     * @param token
     * @param id
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
