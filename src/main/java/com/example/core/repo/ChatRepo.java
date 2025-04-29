package com.example.core.repo;

import com.example.core.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepo extends JpaRepository<Chat, Long> {
    @Query("SELECT DISTINCT c.chatId FROM Chat c JOIN c.filters f")
    List<Long> findAllChatIdsWithFilters();

    @Query("SELECT DISTINCT c.chatId FROM Chat c JOIN c.filters f WHERE f.summary = true")
    List<Long> findAllChatIdsWithSummarize();
}