package com.chatapplicationspringBoot.service;

import com.chatapplicationspringBoot.model.entity.Permission;
import com.chatapplicationspringBoot.repository.PermissionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {
    private static final Logger LOG =  LogManager.getLogger(CategoryService.class);
    PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    /**
     * @Author "Kamran"
     * @Description "This method is listing all the permissions from the database"
     * @return
     */
    public ResponseEntity<Object> ListAllPermissions(){
        try{
            List<Permission> permissionList = permissionRepository.findAll();
            if(permissionList.isEmpty()){
                return new ResponseEntity<>("There are no permissions in the Database", HttpStatus.NOT_FOUND);
            }
            else{
                return new ResponseEntity<>(permissionList, HttpStatus.FOUND);
            }
        }catch (Exception e){
            LOG.info("Error: "+ e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> AddNewPermission(List<Permission> permission){
        if(null==permission){
            return new ResponseEntity<>("You are entering null values", HttpStatus.BAD_REQUEST);
        }
        else {
            try{
                for (Permission newPermission: permission
                     ) {
                    permissionRepository.save(newPermission);
                }
                return new ResponseEntity<>("Permission has been successfully Added", HttpStatus.OK);
            }catch (Exception e){
                LOG.info("Error: "+ e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    public ResponseEntity<Object> DeletePermissionById(Long permissionId){
        Optional<Permission> permission = permissionRepository.findById(permissionId);
        if(permission.isPresent()){
            try{
                permissionRepository.deleteById(permissionId);
                return new ResponseEntity<>("Permission has been successfully deleted", HttpStatus.OK);
            }catch (Exception e){
                LOG.info("Error: "+ e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>("There is no permission against this id", HttpStatus.NOT_FOUND);
        }
    }

}
