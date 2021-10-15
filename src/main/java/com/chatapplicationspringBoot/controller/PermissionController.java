package com.chatapplicationspringBoot.controller;

import com.chatapplicationspringBoot.model.entity.Permission;
import com.chatapplicationspringBoot.service.PermissionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.List;

/**
 * The type Permission controller.
 */
@EnableSwagger2
@RestController
@RequestMapping("/permissions")
public class PermissionController {
    private static final Logger LOG =  LogManager.getLogger(UserController.class);
    private static final String token = "40dc498b-e837-4fa9-8e53-c1d51e01af15";

    /**
     * The Permission service.
     */
    PermissionService permissionService;

    /**
     * Instantiates a new Permission controller.
     *
     * @param permissionService the permission service
     */
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * Authorization boolean.
     *
     * @param token the token
     * @return boolean
     * @Author "Kamran"
     * @Description "Authorizing the token"
     */
    public boolean Authorization(String token) {
        LOG.info("Authorizing the user ");
        return PermissionController.token.equals(token);
    }

    /**
     * Un authorize user response entity.
     *
     * @return response entity
     * @Author "Kamran"
     * @Description "if the user is un-authorized"
     */
    public ResponseEntity<Object> UnAuthorizeUser() {
        LOG.info("Unauthorized user is trying to get access");
        return new ResponseEntity<>("Kindly login first", HttpStatus.UNAUTHORIZED);
    }

    /**
     * List all permissions response entity.
     *
     * @return response entity
     * @Author "Kamran"
     * @Description "This method is listing all the permissions from the database"
     */
    @GetMapping("/list")
    public ResponseEntity<Object> ListAllPermissions(){
            return permissionService.ListAllPermissions();
    }

    /**
     * Add new permission response entity.
     *
     * @param permission the permission
     * @return response entity
     * @Author "Kamran"
     * @Description "This method is Adding the permissions in the database"
     */
    @PostMapping("/add")
    public ResponseEntity<Object> AddNewPermission( @RequestBody List<Permission> permission) {
                return permissionService.AddNewPermission(permission);
    }

    /**
     * Delete permission by id response entity.
     *
     * @param id the id
     * @return response entity
     * @Author "Kamran"
     * @Description "This method is deleting the role by using role id"
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> DeletePermissionById(@RequestParam("id")  Long id){
            return permissionService.DeletePermissionById(id);
    }

    /**
     * Update permissions response entity.
     *
     * @param token      the token
     * @param permission the permission
     * @return response entity
     * @Author "Kamran"
     * @Description
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
