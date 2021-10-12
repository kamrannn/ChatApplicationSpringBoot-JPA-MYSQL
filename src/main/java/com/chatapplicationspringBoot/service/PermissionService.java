package com.chatapplicationspringBoot.service;

import com.chatapplicationspringBoot.model.entity.Category;
import com.chatapplicationspringBoot.model.entity.Permission;
import com.chatapplicationspringBoot.repository.PermissionRepository;
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
            List<Permission> permissionList = permissionRepository.findAllByStatus(true);
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

    /**
     * @Author "Kamran"
     * @Description "This method is adding new permissions in the database"
     * @param permission
     * @return
     */
    public ResponseEntity<Object> AddNewPermission(List<Permission> permission){
        if(null==permission){
            return new ResponseEntity<>("You are entering null values", HttpStatus.BAD_REQUEST);
        }
        else {
            try{
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String date = formatter.format(new Date());
                for (Permission newPermission: permission
                     ) {
                    List<Permission> existingPermission = permissionRepository.findByName(newPermission.getName());
                    if(!existingPermission.isEmpty()){
                        for (Permission changePermission: existingPermission
                             ) {
                            changePermission.setStatus(true);
                        }
                        updatePermission(existingPermission);
                    }else {
                        newPermission.setCreateDate(date);
                        newPermission.setStatus(true);
                        permissionRepository.save(newPermission);
                    }
                }
                return new ResponseEntity<>("Permissions has been successfully Added", HttpStatus.OK);
            }catch (Exception e){
                LOG.info("Error: "+ e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * @Author "Kamran"
     * @Description "This method is deleting the permission from the database against permission id."
     * @param permissionId
     * @return
     */
    public ResponseEntity<Object> DeletePermissionById(Long permissionId){
        Optional<Permission> permission = permissionRepository.findById(permissionId);
        if(permission.isPresent()){
            try{
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String date = formatter.format(new Date());
                permission.get().setStatus(false);
                permission.get().setUpdateDate(date);
                permissionRepository.save(permission.get());
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

    public ResponseEntity<Object> updatePermission(List<Permission> permission) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = formatter.format(new Date());
            for (Permission updatedPermission:permission
                 ) {
                updatedPermission.setUpdateDate(date);
                updatedPermission.setStatus(true);
                permissionRepository.save(updatedPermission);
            }
/*            permission.setUpdateDate(date);
            permission.setStatus(true);
            permissionRepository.save(permission);*/
            return new ResponseEntity<>("Permission has been successfully Updated", HttpStatus.OK);
        } catch (Exception e) {
            LOG.info("Exception: " + e.getMessage());
            return new ResponseEntity<>("Permission is not Updated", HttpStatus.BAD_REQUEST);
        }
    }
}
