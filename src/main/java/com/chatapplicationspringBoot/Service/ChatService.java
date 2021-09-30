package com.chatapplicationspringBoot.Service;

import com.chatapplicationspringBoot.Model.Chat;
import com.chatapplicationspringBoot.Repository.ChatRepository;
import com.chatapplicationspringBoot.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatService {


    //Autowiring the ChatRepository Class
    final private ChatRepository chatRepository;
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Autowired
    private UserRepository userRepository;
/*    public ChatService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }*/

    //Get all chats from the Database
    public List<Chat> ListAllChat(){
        return chatRepository.findAll();
    }

    //Save chat into database by getting values from controller
    public void saveChat(Chat chat) {
        Date date = new Date();
        chat.setAnswerDate(date.toString());
        chat.setQuestionDate(date.toString());
        chat.setUpdateDate(date.toString());
        chatRepository.save(chat);
    }

    //Update chat into database by getting values from controller
    public void updateChat(Chat chat){
        Date date = new Date();
        chat.setUpdateDate(date.toString());
        chatRepository.save(chat);
    }

    //Find chat from database by using ChatID
    public Chat getChat(Long id) {
        return chatRepository.findById(id).get();
    }

    //Delete chat from the database
    public void deleteChat(Long id) {
        chatRepository.deleteById(id);
    }

    public List<Chat> ListAllChatByUserId(Long userID) {
        return chatRepository.findByUserId(userID);
    }

    public Chat CreateUserChat(Long userID, Chat chat) throws Exception {
        return userRepository.findById(userID).map(user -> {
            chat.setUser(user);
            return chatRepository.save(chat);
        }).orElseThrow(() -> new Exception("Not Found"));

    }
}
