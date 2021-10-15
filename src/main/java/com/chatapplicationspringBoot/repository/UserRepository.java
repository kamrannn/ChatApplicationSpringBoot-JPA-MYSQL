package com.chatapplicationspringBoot.repository;

import com.chatapplicationspringBoot.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find user by email and password optional.
     *
     * @param email    the email
     * @param password the password
     * @return the optional
     */
    Optional<User> findUserByEmailAndPassword(String email, String password);

    /**
     * Find users by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<User>  findUsersById(long id);

    /**
     * Find all users by status.
     *
     * @param status the status
     * @return the list
     */
    List<User> findAllByStatus(boolean status);

    /**
     * Find user by id, sms token and email token.
     *
     * @param id         the id
     * @param smsToken   the sms token
     * @param emailToken the email token
     * @return the optional
     */
    Optional<User> findByIdAndSmsTokenAndEmailToken(long id, String smsToken, String emailToken);
}
