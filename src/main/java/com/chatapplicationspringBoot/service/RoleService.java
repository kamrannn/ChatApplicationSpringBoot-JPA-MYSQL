package com.chatapplicationspringBoot.service;

import com.chatapplicationspringBoot.model.entity.Role;
import com.chatapplicationspringBoot.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleService {
    RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

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
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> AddNewRole(Role role){
        if(null==role){
            return new ResponseEntity<>("You are entering null values", HttpStatus.BAD_REQUEST);
        }
        else {
            try{
                roleRepository.save(role);
                return new ResponseEntity<>("Role has been successfully Added", HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }
}
