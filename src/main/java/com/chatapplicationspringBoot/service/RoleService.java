package com.chatapplicationspringBoot.service;

import com.chatapplicationspringBoot.model.entity.Role;
import com.chatapplicationspringBoot.repository.RoleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private static final Logger LOG =  LogManager.getLogger(CategoryService.class);
    RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * @Author "Kamran"
     * @Description "This method is listing all the roles from the database and returning the response.
     * @return
     */
    public ResponseEntity<Object> ListAllRoles(){
        try{
            List<Role> roleList = roleRepository.findAll();
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
     * @Author "Kamran"
     * @Description "This method is adding a new role in the database and returning response."
     * @param role
     * @return
     */
    public ResponseEntity<Object> AddNewRole(Role role){
        if(null==role){
            return new ResponseEntity<>("You are entering null values", HttpStatus.BAD_REQUEST);
        }
        else {
            try{
                roleRepository.save(role);
                return new ResponseEntity<>("Role has been successfully Added", HttpStatus.OK);
            }catch (Exception e){
                LOG.info("Error: "+ e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * @Author "Kamran"
     * @Description "This method is deleting the role from the database using role id and returning response."
     * @param roleId
     * @return
     */
    public ResponseEntity<Object> DeleteRoleById(Long roleId){
        Optional<Role> role = roleRepository.findById(roleId);
        if(role.isPresent()){
            try{
                roleRepository.deleteById(roleId);
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
}
