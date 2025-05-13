package com.example.core.repo;

import com.example.core.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepo extends JpaRepository<Chat, Long> {
    @Query("SELECT DISTINCT c.chatId FROM Chat c JOIN c.filters f")
    List<Long> findAllChatIdsWithFilters();

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Chat c JOIN c.filters f JOIN f.messages m WHERE c.chatId = :chatId AND m.checked = false")
    Boolean checkUnreadByChatId(@Param("chatId") Long chatId);
}