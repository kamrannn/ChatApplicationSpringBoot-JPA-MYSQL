package com.chatapplicationspringBoot.Model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "t_chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id; //Chat ID having a question and answer
    private String question; //variable to store the question
    private String answer; //variable to store the answer
    private String  questionDate; //variable to store the date when the questioned will be made
    private String  answerDate;//variable to store the date when the answer will be made
    private String  updateDate; // variable to store the date when question will be edited/updated
}
