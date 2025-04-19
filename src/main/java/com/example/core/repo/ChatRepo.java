package com.example.core.repo;

import com.example.core.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepo extends JpaRepository<Chat, Long> {

    @Query("SELECT c.chatId FROM Chat c WHERE c.summarize = true OR c.filter IS NOT NULL")
    List<Long> findAllChatIdsWithSummarizeOrFilter();

}