package com.chatapplicationspringBoot.Repository;

import com.chatapplicationspringBoot.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //for getting email if it exists in database
//    User findUserByEmail(String email);
    Optional<User> findUserByEmailAndPassword(String email, String password);
    Optional<User>  findUsersById(long id);
}
