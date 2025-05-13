package com.example.core.repo;

import com.example.core.models.Chat;
import com.example.core.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepo extends JpaRepository<Message,Long> {
    @Query("SELECT m FROM Message m WHERE m.chat.chatId = :id")
    List<Message> findAllByChatId(@Param("id") Long id);

    @Query("SELECT m FROM Message m WHERE m.filter.id = :id ORDER BY m.id ASC")
    List<Message> findAllByFilterId(@Param("id") Long id);
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Message m WHERE m.filter.id = :filterId")
    void deleteAllByFilterId(Long filterId);

    @Query("SELECT CASE WHEN (COUNT(m)>0) THEN true ELSE false END FROM Message m WHERE m.filter.id = :filterId AND m.checked = false")
    Boolean checkUnreadByFilterId(@Param("filterId") Long filterId);

    @Modifying(flushAutomatically = true,clearAutomatically = true)
    @Query("UPDATE Message m SET m.checked = true WHERE m.filter.id = :filterId")
    void markAllAsCheckedByFilterId(@Param("filterId") Long filterId);



    List<Message> findTop1ByChatOrderByTimestampDesc(Chat chat);
}
