package com.chatapplicationspringBoot.model.interfaces;

import com.chatapplicationspringBoot.model.entity.Category;
import com.chatapplicationspringBoot.model.entity.Chat;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class UserDbDTO {
    List<Chat> chatList = new ArrayList<>();
    List<Category> categoryList = new ArrayList<>();
}
