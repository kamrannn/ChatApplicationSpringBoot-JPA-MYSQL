package com.chatapplicationspringBoot.model.interfaces;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class UserDTO {
    // All column of the user to be stored in database
    private long userId;
    private String username;
    private String password;
    private String name;
    private String email;

    List<CategoryDTO> categoryList = new ArrayList<>();
    List<ChatDTO> chatList = new ArrayList<>();
}
