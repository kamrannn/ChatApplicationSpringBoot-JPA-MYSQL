package com.chatapplicationspringBoot.service;

import com.chatapplicationspringBoot.model.entity.Permission;
import com.chatapplicationspringBoot.model.entity.Role;
import com.chatapplicationspringBoot.repository.RoleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The type Role service.
 */
@Service
public class RoleService {
    private static final Logger LOG =  LogManager.getLogger(CategoryService.class);
    /**
     * The Role repository.
     */
    RoleRepository roleRepository;

    /**
     * Instantiates a new Role service.
     *
     * @param roleRepository the role repository
     */
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * List all roles response entity.
     *
     * @return response entity
     * @Author "Kamran"
     * @Description "This method is listing all the roles from the database and returning the response.
     */
    public ResponseEntity<Object> ListAllRoles(){
        try{
//            List<Role> roleList = roleRepository.findAllByStatus(true);
            List<Role> roleList = roleRepository.findAllActiveRolesAndPermissions(true,true);
            if(roleList.isEmpty()){
                return new ResponseEntity<>("There are no roles in the Database", HttpStatus.NOT_FOUND);
            }
            else{
                return new ResponseEntity<>(roleList, HttpStatus.FOUND);
            }
        }catch (Exception e){
            LOG.info("Error: "+ e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Add new role response entity.
     *
     * @param role the role
     * @return response entity
     * @Author "Kamran"
     * @Description "This method is adding a new role in the database and returning response."
     */
    public ResponseEntity<Object> AddNewRole(List<Role> role){
        if(null==role){
            return new ResponseEntity<>("You are entering null values", HttpStatus.BAD_REQUEST);
        }
        else {
            try{
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String date = formatter.format(new Date());
                for (Role newRole: role
                ) {
                    List<Role> existingRole = roleRepository.findByName(newRole.getName());
                    if(!existingRole.isEmpty()){
                        for (Role changeRoleStatus: existingRole
                        ) {
                            changeRoleStatus.setStatus(true);
                        }
                        updateRole(existingRole);
                    }else {
                        newRole.setCreateDate(date);
                        newRole.setStatus(true);
                        roleRepository.save(newRole);
                    }
                }
                return new ResponseEntity<>("Role has been successfully Added", HttpStatus.OK);
            }catch (Exception e){
                LOG.info("Error: "+ e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * Delete role by id response entity.
     *
     * @param roleId the role id
     * @return response entity
     * @Author "Kamran"
     * @Description "This method is deleting the role from the database using role id and returning response."
     */
    public ResponseEntity<Object> DeleteRoleById(Long roleId){
        Optional<Role> role = roleRepository.findById(roleId);
        if(role.isPresent()){
            try{
                roleRepository.deleteById(roleId);
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String date = formatter.format(new Date());
                role.get().setStatus(false);
                role.get().setUpdateDate(date);
                roleRepository.save(role.get());
                return new ResponseEntity<>("Role has been successfully deleted", HttpStatus.OK);
            }catch (Exception e){
                LOG.info("Error: "+ e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>("There is no role against this id", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Update role response entity.
     *
     * @param roleList the role list
     * @return the response entity
     */
    public ResponseEntity<Object> updateRole(List<Role> roleList) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = formatter.format(new Date());
            for (Role updatedRoles:roleList
            ) {
                updatedRoles.setUpdateDate(date);
                updatedRoles.setStatus(true);
                roleRepository.save(updatedRoles);
            }
            return new ResponseEntity<>("Roles has been successfully Updated", HttpStatus.OK);
        } catch (Exception e) {
            LOG.info("Exception: " + e.getMessage());
            return new ResponseEntity<>("Roles are not Updated", HttpStatus.BAD_REQUEST);
        }
    }
}
