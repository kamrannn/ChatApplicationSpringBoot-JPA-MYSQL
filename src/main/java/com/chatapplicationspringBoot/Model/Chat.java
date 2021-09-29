package com.chatapplicationspringBoot.Model;

import javax.persistence.*;
import java.util.Date;
@Entity
public class Chat {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id; //Chat ID having a question and answer
    @Column(nullable = false,unique = true)
    private String question; //Chat question
    @Column(nullable = false)
    private String answer; //Chat answer
    @Column(nullable = false)
    private String  questionDate;
    @Column(nullable = false)
    private String  answerDate;
    @Column(nullable = false)
    private String  updateDate;

    public Chat() {
    }

    public Chat(long id,String question, String answer, String questionDate, String answerDate) {
        this.id= id;
        this.question = question;
        this.answer = answer;
        this.questionDate=questionDate;
        this.answerDate= answerDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String  getQuestionDate() {
        return questionDate;
    }

    public void setQuestionDate(String  questionDate) {
        this.questionDate = questionDate;
    }

    public String  getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(String  answerDate) {
        this.answerDate = answerDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
