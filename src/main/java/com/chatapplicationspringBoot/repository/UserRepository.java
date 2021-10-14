package com.chatapplicationspringBoot.repository;

import com.chatapplicationspringBoot.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailAndPassword(String email, String password);
    Optional<User>  findUsersById(long id);
    List<User> findAllByStatus(boolean status);
    Optional<User> findByIdAndSmsTokenAndEmailToken(long id, String smsToken, String emailToken);
}
