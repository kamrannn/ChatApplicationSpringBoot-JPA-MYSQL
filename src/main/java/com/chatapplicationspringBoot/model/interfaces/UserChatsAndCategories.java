package com.chatapplicationspringBoot.model.interfaces;

import java.util.ArrayList;
import java.util.List;

public class UserChatsAndCategories {
    List<Object> userCategoriesList = new ArrayList<>();
    List<Object> userChatList = new ArrayList<>();

    public List<Object> getUserCategoriesList() {
        return userCategoriesList;
    }

    public void setUserCategoriesList(List<Object> userCategoriesList) {
        this.userCategoriesList = userCategoriesList;
    }

    public List<Object> getUserChatList() {
        return userChatList;
    }

    public void setUserChatList(List<Object> userChatList) {
        this.userChatList = userChatList;
    }
}
