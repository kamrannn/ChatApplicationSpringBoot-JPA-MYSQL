package com.chatapplicationspringBoot.Model;

import javax.persistence.*;
import java.util.Date;
@Entity
public class User {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id; //variable to store the User ID
    @Column(nullable = false)
    private String firstName; //variable to store the User First Name
    private String lastName;//variable to store the User Last Name
    @Column(nullable = false,unique = true)
    private String email;//variable to store the User email
    private int age;//variable to store the User age
    @Column(nullable = false)
    private String password; //variable to store the User Password

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

    public String getSecondName() {
        return lastName;
    }

    public void setSecondName(String lastName) {
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

/*    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }*/

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
