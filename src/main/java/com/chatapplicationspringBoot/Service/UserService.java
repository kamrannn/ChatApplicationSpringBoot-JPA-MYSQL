package com.chatapplicationspringBoot.Service;

import com.chatapplicationspringBoot.Model.User;
import com.chatapplicationspringBoot.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> listAllUser() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

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
