package com.chatapplicationspringBoot.model.interfaces.thirdpartyDTO;

import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type User chats and categories.
 */
@Data
public class UserChatsAndCategories implements Serializable {
    /**
     * A list of user categories that will come from 3rd Party API.
     */
    List<CategoryDTO> userCategoriesList = new ArrayList<>();
    /**
     * A list of user chats that will come from 3rd Party API.
     */
    List<ChatDTO> userChatList = new ArrayList<>();
}
