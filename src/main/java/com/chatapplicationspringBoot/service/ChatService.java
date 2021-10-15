package com.chatapplicationspringBoot.service;

import com.chatapplicationspringBoot.controller.ChatController;
import com.chatapplicationspringBoot.model.entity.Chat;
import com.chatapplicationspringBoot.repository.ChatRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The type Chat service.
 * @Author "Kamran"
 * @CreatedData "12-10-2021"
 */
@Service
public class ChatService {
    private static final Logger LOG =  LogManager.getLogger(ChatController.class);

    /**
     * Instantiating the chat Repository
     */
    final private ChatRepository chatRepository;

    /**
     * Instantiates a new Chat service.
     * @Author "Kamran"
     * @CreatedData "12-10-2021"
     * @param chatRepository the chat repository
     */
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    /**
     * List all chats from the Database.
     * @Author "Kamran"
     * @CreatedData "12-10-2021"
     * @return the response entity
     */
    public ResponseEntity<Object> ListAllChat(){
        try{
            List<Chat> chatList=  chatRepository.findAll();
            if(chatList.isEmpty()){
                return new ResponseEntity<>("There are no records in the list", HttpStatus.NOT_FOUND);
            }
            else{
                return new ResponseEntity<>(chatList, HttpStatus.OK);
            }
        }catch (Exception e){
            LOG.info("Exception: "+e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update chat into database by getting values from controller.
     * @Author "Kamran"
     * @CreatedData "12-10-2021"
     * @param updatedChat the updated chat
     * @return response entity
     */
    public ResponseEntity<Object> updateChat(List<Chat> updatedChat){
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            for (Chat chat:updatedChat) {
                String date = formatter.format(new Date());
                chat.setUpdateDate(date);
                chatRepository.save(chat);
            }
            return new ResponseEntity<>("Chat has been successfully Updated", HttpStatus.OK);
        }catch (Exception e){
            LOG.info("Exception: "+e.getMessage());
            return new ResponseEntity<>("Chat is not Updated", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Find chat from database by using ChatID.
     * @Author "Kamran"
     * @CreatedData "12-10-2021"
     * @param id the id
     * @return the chat
     */
    public ResponseEntity<Object> getChat(Long id) {
        try {
            Optional<Chat> chat = chatRepository.findById(id);
            if(chat.isPresent()){
                return new ResponseEntity<>(chat, HttpStatus.OK);
            }
            else{
                LOG.info("NO chat Exists Against this Question ID: "+id);
                return new ResponseEntity<>("There is no chat available against this Chat ID: "+id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.info("Exception: "+e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete specific chat from the database by using chat Id.
     * @Author "Kamran"
     * @CreatedData "12-10-2021"
     * @param id the id
     * @return response entity
     */
    public ResponseEntity<Object> deleteChat(Long id) {
        try{
            chatRepository.deleteById(id);
            return new ResponseEntity<>("The selected chat is successfully deleted", HttpStatus.OK);
        }catch (Exception e){
            LOG.info("Exception: "+ e.getMessage());
            return new ResponseEntity<>("The selected chat doesn't exist in the database", HttpStatus.BAD_REQUEST);
        }
    }
}
