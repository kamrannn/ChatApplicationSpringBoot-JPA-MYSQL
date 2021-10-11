package com.chatapplicationspringBoot.controller;

import com.chatapplicationspringBoot.model.entity.Permission;
import com.chatapplicationspringBoot.model.entity.Role;
import com.chatapplicationspringBoot.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@EnableSwagger2
@RestController
@RequestMapping("/permissions")
public class PermissionController {
    PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> ListAllPermissions(){
            return permissionService.ListAllPermissions();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> AddNewPermission( @RequestBody List<Permission> permission) {
                return permissionService.AddNewPermission(permission);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> DeleteRoleById(@RequestParam("id")  Long id){
            return permissionService.DeletePermissionById(id);
    }
}
