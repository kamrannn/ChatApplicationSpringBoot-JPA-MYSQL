package com.chatapplicationspringBoot.controller;

import com.chatapplicationspringBoot.model.entity.Role;
import com.chatapplicationspringBoot.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/roles")
public class RoleController {
    private static final String userToken = "40dc498b-e837-4fa9-8e53-c1d51e01af15u";
    private static final String adminToken = "40dc498b-e837-4fa9-8e53-c1d51e01af15a";

    RoleService roleService;
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    public String Authorization(String token) {
        if(token.equals(userToken)){
            return "user";
        }
        else if(token.equals(adminToken)){
            return "admin";
        }
        return null;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> ListAllRoles(@RequestHeader("Authorization") String token){
        if(Authorization(token).equals("admin")){
            return roleService.ListAllRoles();
        }
        else{
            return new ResponseEntity<>("You are not an admin to access this API", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> AddNewRole(@RequestHeader("Authorization") String token, @RequestBody Role role){
        try{
            if(Authorization(token).equals("admin")){
                return roleService.AddNewRole(role);
            }
            else{
                return new ResponseEntity<>("You are not an admin to access this API", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>("You are entering the authorization in the wrong way", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> DeleteRoleById(@RequestHeader("Authorization") String token, @RequestParam("id")  Long id){
        if(Authorization(token).equals("admin")){
            return roleService.DeleteRoleById(id);
        }
        else{
            return new ResponseEntity<>("You are not an admin to access this API", HttpStatus.BAD_REQUEST);
        }
    }
}
