package com.chatapplicationspringBoot.Service;

import com.chatapplicationspringBoot.Model.User;
import com.chatapplicationspringBoot.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    // Autowired, Constructor is made
    final private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Get all users from Database
    public List<User> listAllUser() {
        return userRepository.findAll();
    }

    //Save User into database by getting values from controller
    public void saveUser(User user) {
        userRepository.save(user);
    }

    //Find by ID User from database using userID
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    //Delete user from db by using user ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    //Update user into database by getting values from controller
    public void updateUser(User user) {
        userRepository.save(user);
    }

    //checking the email, whether it exists in the Db or not
    public Integer FindByEmail(String email, String password){
        try{
            User user = userRepository.findUserByEmail(email);
            if(user.getPassword().equals(password)){
                return 1;
            }else {
                return 2;
            }

        }catch(Exception exception){
            return 3;
        }
    }
}
