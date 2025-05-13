package com.example.core.repo;

import com.example.core.models.Filter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilterRepo extends JpaRepository<Filter, Long> {
    List<Filter> findByChatChatIdOrderByIdAsc(Long chatId);
    void deleteByChatChatId(Long chatId);
}