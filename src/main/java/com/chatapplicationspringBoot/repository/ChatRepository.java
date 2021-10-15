package com.chatapplicationspringBoot.repository;

import com.chatapplicationspringBoot.model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Chat repository.
 */
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
}
