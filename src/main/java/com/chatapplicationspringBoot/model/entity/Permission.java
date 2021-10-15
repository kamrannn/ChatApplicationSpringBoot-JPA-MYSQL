package com.chatapplicationspringBoot.model.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * Permission class to store the permission properties.
 * @Author "Kamran"
 * @CreatedDate "10-12-2021"
 */
@Data
@Entity
@Table(name = "t_permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id;
    @Column(unique = true)
    private String name; //variable to store the role name
    private String createDate; //variable to store the creation date of permission
    private String updateDate; //variable to store the update date of permission
    private boolean status; //variable to store delete status of the permission
}
