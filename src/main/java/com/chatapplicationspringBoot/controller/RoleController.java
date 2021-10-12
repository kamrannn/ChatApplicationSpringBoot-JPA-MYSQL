package com.chatapplicationspringBoot.controller;

import com.chatapplicationspringBoot.model.entity.Permission;
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

@EnableSwagger2
@RestController
@RequestMapping("/roles")
public class RoleController {
    private static final Logger LOG =  LogManager.getLogger(CategoryService.class);
    private static final String token = "40dc498b-e837-4fa9-8e53-c1d51e01af15";

    RoleService roleService;
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * @Author "Kamran"
     * @Description "Authorizing the token"
     * @param token
     * @return
     */
    public boolean Authorization(String token) {
        LOG.info("Authorizing the user ");
        return RoleController.token.equals(token);
    }

    /**
     * @Author "Kamran"
     * @Description "if the user is un-authorized"
     * @return
     */
    public ResponseEntity<Object> UnAuthorizeUser() {
        LOG.info("Unauthorized user is trying to get access");
        return new ResponseEntity<>("Kindly do the authorization first", HttpStatus.UNAUTHORIZED);
    }

    /**
     * @Author "Kamran"
     * @Description "The request to list the roles will land on this controller, and it will list all the roles"
     * @param token
     * @return
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
     * @Author "Kamran"
     * @Description "This method is adding the new roles in the database."
     * @param token
     * @param role
     * @return
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
     * @Author "Kamran"
     * @Description "This method is deleting the role from the database using role Id"
     * @param token
     * @param id
     * @return
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
     * @Author "Kamran"
     * @Description
     * @param token
     * @param roleList
     * @return
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
