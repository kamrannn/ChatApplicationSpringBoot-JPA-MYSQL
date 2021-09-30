package com.chatapplicationspringBoot.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
@Entity
public class Chat {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id; //Chat ID having a question and answer
    private String question; //variable to store the question
    private String answer; //variable to store the answer
    private String  questionDate; //variable to store the date when the questioned will be made
    private String  answerDate;//variable to store the date when the answer will be made
    private String  updateDate; // variable to store the date when question will be edited/updated

/*    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;*/

    private String userName;


/*    @ManyToOne
    @JsonIgnore
    private User user;*/

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties("chatList")
    private User user;

    public Chat() {
    }

    public Chat(long id,String question, String answer, String questionDate, String answerDate) {
        this.id= id;
        this.question = question;
        this.answer = answer;
        this.questionDate=questionDate;
        this.answerDate= answerDate;
    }

    public Chat(long id,String question, String answer, String questionDate, String answerDate, User user) {
        this.id= id;
        this.question = question;
        this.answer = answer;
        this.questionDate=questionDate;
        this.answerDate= answerDate;
        this.user=user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserName() {
        return getUser().getFirstName();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
