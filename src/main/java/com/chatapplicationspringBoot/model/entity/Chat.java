package com.chatapplicationspringBoot.model.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "t_chat")
/**
 * @Author "Kamran"
 * @Description "Chat class to store the chat properties"
 * @CreatedDate "10-12-2021"
 */
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id; //Chat ID having a question and answer
    private String question; //variable to store the question
    private String answer; //variable to store the answer
    private String createDate; // variable to store the date when chat will be created
    private String  updateDate; // variable to store the date when chat will be edited/updated
    private boolean status; //variable to store delete status of the permission
}
