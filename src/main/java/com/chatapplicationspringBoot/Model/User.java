package com.chatapplicationspringBoot.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id; //variable to store the User ID
    private String firstName; //variable to store the User First Name
    private String lastName;//variable to store the User Last Name
    @Column(unique = true)
    private String email;//variable to store the User email
    private int age;//variable to store the User age
    private String password; //variable to store the User Password

    @OneToMany(targetEntity = Chat.class,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private List<Chat> chats=new ArrayList<>();

    public User() {
    }

    //Constructor to set the values
    public User(long id,String firstName, String lastName, String email, int age, String password) {
        this.id= id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email=email;
        this.age= age;
        this.password=password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }
}
