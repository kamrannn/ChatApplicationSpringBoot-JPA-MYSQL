package com.chatapplicationspringBoot.model.interfaces.databaseDTO;

import com.chatapplicationspringBoot.model.entity.Category;
import com.chatapplicationspringBoot.model.entity.Chat;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
public class UserChatAndCategoriesDB implements Serializable {
    List<Chat> chatList = new ArrayList<>(); //List of user Chats List from the database
    List<Category> categoryList = new ArrayList<>(); //List of user Categories List from the database
}
