package com.chatapplicationspringBoot.Repository;

import com.chatapplicationspringBoot.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //for getting email if it exists in database
    User findUserByEmail(String email);
/*
    Optional<User> findByName(String name);
*/


}
