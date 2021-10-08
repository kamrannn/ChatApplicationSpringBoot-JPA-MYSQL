package com.chatapplicationspringBoot.repository;

import com.chatapplicationspringBoot.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //for getting email if it exists in database
//    User findUserByEmail(String email);
    Optional<User> findUserByEmailAndPassword(String email, String password);
    Optional<User>  findUsersById(long id);
}
