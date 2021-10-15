package com.chatapplicationspringBoot.model.interfaces.databaseDTO;

import com.chatapplicationspringBoot.model.entity.Category;
import com.chatapplicationspringBoot.model.entity.Chat;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type User chat and categories db.
 */
@Data
public class UserChatAndCategoriesDB implements Serializable {
    /**
     * The Chat list.
     */
    List<Chat> chatList = new ArrayList<>(); //List of user Chats List from the database
    /**
     * The Category list.
     */
    List<Category> categoryList = new ArrayList<>(); //List of user Categories List from the database
}
