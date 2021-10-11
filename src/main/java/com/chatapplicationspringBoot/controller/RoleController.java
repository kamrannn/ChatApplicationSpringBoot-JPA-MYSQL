package com.chatapplicationspringBoot.controller;

import com.chatapplicationspringBoot.model.entity.Role;
import com.chatapplicationspringBoot.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/roles")
public class RoleController {
    RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> ListAllRoles(){
        return roleService.ListAllRoles();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> AddNewRole(@RequestBody Role role){
        return roleService.AddNewRole(role);
    }
}
