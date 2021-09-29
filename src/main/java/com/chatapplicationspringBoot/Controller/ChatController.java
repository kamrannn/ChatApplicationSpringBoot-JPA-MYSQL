package com.chatapplicationspringBoot.Controller;

import com.chatapplicationspringBoot.Model.Chat;
import com.chatapplicationspringBoot.Model.User;
import com.chatapplicationspringBoot.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    ChatService chatService;
    private String key = "40dc498b-e837-4fa9-8e53-c1d51e01af15";

    public boolean authorization(String key1) {
        return key.equals(key1);
    }

    public ResponseEntity<Object> UnAuthorizeUser() {
        return new ResponseEntity<>("Kindly login first", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("")
    public ResponseEntity<Object> list(@RequestHeader("Authorization") String key1) {

        if (authorization(key1)) {
            try{
                List<Chat> chatList= chatService.ListAllChat();
                return new ResponseEntity<>(chatList,HttpStatus.NOT_FOUND);
            }catch (Exception e){
                return new ResponseEntity<>("There is no data in chat table ",HttpStatus.NOT_FOUND);
            }
        } else {
            return UnAuthorizeUser();
        }
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<Object> GetQuestion(@RequestHeader("Authorization") String key1, @PathVariable Long id) {
        if (authorization(key1)) {
            try {
                Chat chat = chatService.getChat(id);
                return new ResponseEntity<>(chat, HttpStatus.OK);
            } catch (NoSuchElementException e) {
                return new ResponseEntity<>("No chat exists against this ID",HttpStatus.NOT_FOUND);
            }
        }else {
            return UnAuthorizeUser();
        }
    }

    @GetMapping("/question")
    public ResponseEntity<Object> GetById(@RequestHeader("Authorization") String key1,@RequestParam("question") Long id){

        if (authorization(key1)) {
            try {
                Chat chat = chatService.getChat(id);
                return new ResponseEntity(chat, HttpStatus.OK);
            } catch (NoSuchElementException e) {
                return new ResponseEntity("There is no data available for this chat ID", HttpStatus.NOT_FOUND);
            }
        }
        else{
            return UnAuthorizeUser();
        }
    }

    @PostMapping("/add")
    public ResponseEntity Add(@RequestHeader("Authorization") String key1, @RequestBody Chat chat) {
        if (authorization(key1)) {
            try{
                chatService.saveChat(chat);
                return new ResponseEntity("Chat has been successfully added",HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity("The Question already Exists", HttpStatus.CONFLICT);
            }
        }
        else{
            return UnAuthorizeUser();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> Update(@RequestBody Chat chat) {
        try{
            chatService.updateChat(chat);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete( @PathVariable Long id,@RequestHeader("Authorization") String key1) {

        if (authorization(key1) == true) {
            chatService.deleteChat(id);
        }
    }

    @DeleteMapping("/delete")
    public void delete(@RequestHeader("Authorization") String key1,@RequestParam ("delete") Long id) {
        if (authorization(key1) == true) {
            chatService.deleteChat(id);
        }
    }
}
