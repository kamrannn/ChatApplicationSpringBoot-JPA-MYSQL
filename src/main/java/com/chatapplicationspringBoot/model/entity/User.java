package com.chatapplicationspringBoot.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "t_user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
/**
 * @Author "Kamran"
 * @Description "User class to store the user properties"
 * @CreatedDate "10-12-2021"
 */
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id; //variable to store the User ID
    private String firstName; //variable to store the User First Name
    private String lastName; //variable to store the User Last Name
    @Column(unique = true)
    private String email; //variable to store the User email
    private int age; //variable to store the User age
    private String password; //variable to store the User Password
    private String createDate; // variable to store the user when question will be created
    private String updateDate; // variable to store the date when user will be edited/updated
    private String phoneNo; // variable to store the date when user will be edited/updated
    private boolean status; //variable to store delete status of the permission
    private String smsToken;
    private String emailToken;

    @OneToMany(targetEntity = Chat.class,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private List<Chat> chats=new ArrayList<>();

    @ManyToMany(targetEntity = Category.class,fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(targetEntity = Role.class,fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Role> roles = new ArrayList<>();
}
