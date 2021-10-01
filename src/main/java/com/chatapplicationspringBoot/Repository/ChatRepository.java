package com.chatapplicationspringBoot.Repository;

import com.chatapplicationspringBoot.Model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
/*    List<Chat> findByUserId(Long userId);
    Optional<Chat> findByIdAndUserId(Long id, Long userId);*/

}
