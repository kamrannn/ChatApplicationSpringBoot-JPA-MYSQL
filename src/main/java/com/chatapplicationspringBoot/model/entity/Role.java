package com.chatapplicationspringBoot.model.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "t_role")
/**
 * @Author "Kamran"
 * @Description "Role class to store the role properties"
 * @CreatedDate "10-12-2021"
 */
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id; //variable to store the role id
    @Column(unique = true)
    private String name; //variable to store the role name
    private String description; //variable to store the role description
    private String createDate; //variable to store the creation date of permission
    private String updateDate; // variable to store the date when role will be edited/updated
    private boolean status;
    @ManyToMany(targetEntity = Permission.class,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Permission> permissions = new ArrayList<>();
}
