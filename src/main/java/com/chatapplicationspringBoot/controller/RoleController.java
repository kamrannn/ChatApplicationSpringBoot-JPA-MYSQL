package com.chatapplicationspringBoot.controller;

import com.chatapplicationspringBoot.model.entity.Role;
import com.chatapplicationspringBoot.service.CategoryService;
import com.chatapplicationspringBoot.service.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.List;


/**
 * The type Role controller.
 */
@EnableSwagger2
@RestController
@RequestMapping("/roles")
public class RoleController {
    private static final Logger LOG =  LogManager.getLogger(CategoryService.class);
    private static final String token = "40dc498b-e837-4fa9-8e53-c1d51e01af15";

    /**
     * The Role service.
     */
    RoleService roleService;

    /**
     * Instantiates a new Role controller.
     *
     * @param roleService the role service
     */
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
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
        return RoleController.token.equals(token);
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
        return new ResponseEntity<>("Kindly do the authorization first", HttpStatus.UNAUTHORIZED);
    }

    /**
     * List all roles response entity.
     *
     * @param token the token
     * @return response entity
     * @Author "Kamran"
     * @Description "The request to list the roles will land on this controller, and it will list all the roles"
     */
    @GetMapping("/list")
    public ResponseEntity<Object> ListAllRoles(@RequestHeader("Authorization") String token){
        if(Authorization(token)){
            return roleService.ListAllRoles();
        }
        else{
            return new ResponseEntity<>("You are not an admin to access this API", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Add new role response entity.
     *
     * @param token the token
     * @param role  the role
     * @return response entity
     * @Author "Kamran"
     * @Description "This method is adding the new roles in the database."
     */
    @PostMapping("/add")
    public ResponseEntity<Object> AddNewRole(@RequestHeader("Authorization") String token, @RequestBody List<Role> role){
            if(Authorization(token)){
                return roleService.AddNewRole(role);
            }
            else{
                return new ResponseEntity<>("You are not an admin to access this API", HttpStatus.BAD_REQUEST);
            }
    }

    /**
     * Delete role by id response entity.
     *
     * @param token the token
     * @param id    the id
     * @return response entity
     * @Author "Kamran"
     * @Description "This method is deleting the role from the database using role Id"
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> DeleteRoleById(@RequestHeader("Authorization") String token, @RequestParam("id")  Long id){
        if(Authorization(token)){
            return roleService.DeleteRoleById(id);
        }
        else{
            return new ResponseEntity<>("You are not an admin to access this API", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update role response entity.
     *
     * @param token    the token
     * @param roleList the role list
     * @return response entity
     * @Author "Kamran"
     * @Description
     */
    @PutMapping("/update")
    public ResponseEntity<Object> UpdateRole(@RequestHeader("Authorization") String token,@RequestBody List<Role> roleList) {
        if (Authorization(token)) {
            try {
                return roleService.updateRole(roleList);
            } catch (Exception exception) {
                LOG.info("Error: "+ exception.getMessage());
                return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return UnAuthorizeUser() ;
        }
    }
}
