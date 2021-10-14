package com.chatapplicationspringBoot.controller;

import com.chatapplicationspringBoot.model.entity.Category;
import com.chatapplicationspringBoot.model.entity.Permission;
import com.chatapplicationspringBoot.model.entity.Role;
import com.chatapplicationspringBoot.service.PermissionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@EnableSwagger2
@RestController
@RequestMapping("/permissions")
public class PermissionController {
    private static final Logger LOG =  LogManager.getLogger(UserController.class);
    private static final String token = "40dc498b-e837-4fa9-8e53-c1d51e01af15";

    PermissionService permissionService;
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * @Author "Kamran"
     * @Description "Authorizing the token"
     * @param token
     * @return
     */
    public boolean Authorization(String token) {
        LOG.info("Authorizing the user ");
        return PermissionController.token.equals(token);
    }

    /**
     * @Author "Kamran"
     * @Description "if the user is un-authorized"
     * @return
     */
    public ResponseEntity<Object> UnAuthorizeUser() {
        LOG.info("Unauthorized user is trying to get access");
        return new ResponseEntity<>("Kindly login first", HttpStatus.UNAUTHORIZED);
    }

    /**
     * @Author "Kamran"
     * @Description "This method is listing all the permissions from the database"
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<Object> ListAllPermissions(){
            return permissionService.ListAllPermissions();
    }

    /**
     * @Author "Kamran"
     * @Description "This method is Adding the permissions in the database"
     * @param permission
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Object> AddNewPermission( @RequestBody List<Permission> permission) {
                return permissionService.AddNewPermission(permission);
    }

    /**
     * @Author "Kamran"
     * @Description "This method is deleting the role by using role id"
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> DeletePermissionById(@RequestParam("id")  Long id){
            return permissionService.DeletePermissionById(id);
    }

    /**
     * @Author "Kamran"
     * @Description
     * @param token
     * @param permission
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Object> UpdatePermissions(@RequestHeader("Authorization") String token,@RequestBody List<Permission> permission) {
        if (Authorization(token)) {
            try {
                return permissionService.updatePermission(permission);
            } catch (Exception exception) {
                LOG.info("Error: "+ exception.getMessage());
                return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return UnAuthorizeUser() ;
        }
    }
}
