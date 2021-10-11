package com.chatapplicationspringBoot.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id;
    @Column(unique = true)
    private String email; //variable to store the role name
}
