package com.chatapplicationspringBoot.model.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Category class to store the categories properties
 * @Author "Kamran"
 * @CreatedDate "10-12-2021"
 */
@Data
@Entity
@Table(name = "t_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false,unique = true)
    private String name;
    private String createDate; // variable to store the date when category will be edited/updated
    private String updateDate; // variable to store the date when category will be edited/updated
    private boolean status;
}
