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
    final ChatService chatService;
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    //key value for the authorization
    private String token = "40dc498b-e837-4fa9-8e53-c1d51e01af15";

    /**
     *
     * @param userToken
     * @return true or false
     */
    public boolean authorization(String userToken) {
        return token.equals(token);
    }

    public ResponseEntity<Object> UnAuthorizeUser() {
        return new ResponseEntity<>("Kindly login first", HttpStatus.UNAUTHORIZED);
    }

    //This API shows all the chats
    @GetMapping("")
    public ResponseEntity<Object> list(@RequestHeader("Authorization") String token) {
        if (authorization(token)) {
            try{
                List<Chat> chatList= chatService.ListAllChat();
                return new ResponseEntity<>(chatList,HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>("There is no data in chat table ",HttpStatus.NOT_FOUND);
            }
        } else {
            return UnAuthorizeUser();
        }
    }

    //This API only show certain object by taking on ID number as a Path variable
    @GetMapping("/question/{id}")
    public ResponseEntity<Object> GetQuestion(@RequestHeader("Authorization") String token, @PathVariable Long id) {

        if (authorization(token)) {
            try {
                Chat chat = chatService.getChat(id);
                return new ResponseEntity<>(chat, HttpStatus.OK);
            } catch (NoSuchElementException e) {
                LOG.error("NO chat Exists Against this Question ID: "+id,e.getMessage());
                return new ResponseEntity<>("No chat exists against this ID",HttpStatus.NOT_FOUND);
            }
        }else {
            return UnAuthorizeUser();
        }
    }

    //This API only show certain object by taking on ID number in Request parameter
    @GetMapping("/question")
    public ResponseEntity<Object> GetById(@RequestHeader("Authorization") String token,@RequestParam("question") Long id){

        if (authorization(token)) {
            try {
                Chat chat = chatService.getChat(id);
                return new ResponseEntity(chat, HttpStatus.OK);
            } catch (NoSuchElementException e) {
                LOG.error("NO chat Exists Against this Question ID: "+id,e.getMessage());
                return new ResponseEntity("There is no data available for this chat ID", HttpStatus.NOT_FOUND);
            }
        }
        else{
            return UnAuthorizeUser();
        }
    }


    /**
     * @author : Kamran Abbasi
     * @description : This API just add the chat to the database
     * @param token
     * @param chat
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity Add(@RequestHeader("Authorization") String token, @RequestBody Chat chat) {
        if (authorization(token)) {
            try{
                chatService.saveChat(chat);
                return new ResponseEntity("Chat has been successfully added",HttpStatus.OK);
            } catch (Exception e) {
                LOG.error("The Question already Exists: "+chat.getQuestion(), e.getMessage());
                return new ResponseEntity("The Question ("+chat.getQuestion()+") already Exists, kindly change the question", HttpStatus.CONFLICT);
            }
        }
        else{
            return UnAuthorizeUser();
        }
    }

    /**
     * @author Kamran Abbasi
     * @description This API updates the chat by passing the Chat object
     * @param chat
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Object> Update(@RequestBody Chat chat) {
        try{
            chatService.updateChat(chat);
            return new ResponseEntity<>("The Chat has been updated",HttpStatus.OK);
        } catch (NoSuchElementException e) {
            LOG.error("The Chat is not updated because of: "+e.getMessage());
            return new ResponseEntity<>("The Chat is not updated",HttpStatus.NOT_FOUND);
        }
    }

    //This API deletes certain chat using Path variable
    @DeleteMapping("/delete/{id}")
    public void delete( @PathVariable Long id,@RequestHeader("Authorization") String token) {

        if (authorization(token)) {
            chatService.deleteChat(id);
        }
    }

    //This API deletes certain chat using request parameter
    @DeleteMapping("/delete")
    public void delete(@RequestHeader("Authorization") String token,@RequestParam ("delete") Long id) {
        if (authorization(token)) {
            chatService.deleteChat(id);
        }
    }

    @GetMapping("/user/{userId}/chat")
    public ResponseEntity<Object> ListCourseById(@PathVariable(value = "userId") Long userId) {
        List<Chat> chatList = chatService.ListAllChatByUserId(userId);
        return new ResponseEntity(chatList, HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/chat")
    public ResponseEntity<Object> CreateChatAgainstUser(@PathVariable(value = "userId") Long userId, @RequestBody Chat chat) throws Exception {
        chatService.CreateUserChat(userId, chat);
        return new ResponseEntity("OK", HttpStatus.OK);
    }
}
