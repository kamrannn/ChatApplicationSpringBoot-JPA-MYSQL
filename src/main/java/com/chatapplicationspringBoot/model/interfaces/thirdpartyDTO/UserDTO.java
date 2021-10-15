package com.chatapplicationspringBoot.model.interfaces.thirdpartyDTO;

import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type User dto.
 */
@Data
public class UserDTO implements Serializable {
    private long userId; //user ID that will come from 3rd Party API
    private String username; //username that will come from 3rd Party API
    private String password; //Users Password that will come from 3rd Party API
    private String name; //Users name that will come from 3rd Party API
    private String email; //Users email that will come from 3rd Party API
    private String createdDate;
    private String updatedDate;
    private boolean status;
    private String contactNum;

    /**
     * List of categories that will come from the 3rd Party API.
     */
    List<CategoryDTO> categoryList = new ArrayList<>();
    /**
     * List of Chats that will come from the 3rd party API.
     */
    List<ChatDTO> chatList = new ArrayList<>();
}
