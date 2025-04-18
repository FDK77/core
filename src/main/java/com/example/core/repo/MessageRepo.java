package com.example.core.repo;

import com.example.core.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message,Long> {
    @Query("SELECT m FROM Message m WHERE m.chat.chatId = :id")
    List<Message> findAllByChatId(@Param("id") Long id);

}
