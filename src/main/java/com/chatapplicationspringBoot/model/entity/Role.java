package com.chatapplicationspringBoot.model.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "t_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id;
    @Column(unique = true)
    private String name; //variable to store the role name
    private String description;

    @ManyToMany(targetEntity = Permission.class,fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Permission> permissions = new ArrayList<>();
}
