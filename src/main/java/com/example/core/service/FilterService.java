package com.example.core.service;

import com.example.core.models.Chat;
import com.example.core.models.Filter;
import com.example.core.repo.FilterRepo;
import com.example.core.dto.FilterDto;
import com.example.core.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FilterService {

    private final FilterRepo filterRepo;
    private final MessageRepo messageRepo;
    @Autowired
    public FilterService(FilterRepo filterRepo, MessageRepo messageRepo) {
        this.filterRepo = filterRepo;
        this.messageRepo = messageRepo;
    }

    public List<FilterDto> getFilterDtosByChatId(Long chatId) {
        List<Filter> filters = filterRepo.findByChatChatIdOrderByIdAsc(chatId);

        return filters.stream()
                .map(f -> new FilterDto(
                        f.getId(),
                        f.getValue(),
                        f.getSummary(),
                        f.getColor(),
                        f.getName(),
                        messageRepo.checkUnreadByFilterId(f.getId())
                ))
                .toList();
    }

    public List<Filter> findByChatId(Long chatId) {
        return filterRepo.findByChatChatIdOrderByIdAsc(chatId);
    }

    public Filter findById(Long id) {
        return filterRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Filter not found with id: " + id));
    }


    @Transactional
    public void markAsCheckedByFilterId(Long filterId){
        messageRepo.markAllAsCheckedByFilterId(filterId);
    }

    public Filter save(Filter filter) {
        return filterRepo.save(filter);
    }

    public List<Filter> saveAll(List<Filter> filters) {
        return filterRepo.saveAll(filters);
    }

    public void deleteByChatId(Long chatId) {
        filterRepo.deleteByChatChatId(chatId);
    }

    public void delete(Filter filter) {
        filterRepo.delete(filter);
    }

}