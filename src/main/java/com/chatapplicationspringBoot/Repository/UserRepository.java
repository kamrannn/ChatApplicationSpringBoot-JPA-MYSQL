package com.chatapplicationspringBoot.Repository;

import com.chatapplicationspringBoot.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //for getting email if it exists in database
    User findUserByEmail(String email);
    User findUsersById(long id);
}
