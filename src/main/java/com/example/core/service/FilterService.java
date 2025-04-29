package com.example.core.service;

import com.example.core.models.Chat;
import com.example.core.models.Filter;
import com.example.core.repo.FilterRepo;
import com.example.core.dto.FilterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FilterService {

    private FilterRepo filterRepo;

    @Autowired
    public FilterService(FilterRepo filterRepo) {
        this.filterRepo = filterRepo;
    }

    public List<Filter> findByChatId(Long chatId) {
        return filterRepo.findByChatChatId(chatId);
    }

    public Filter findById(Long id) {
        return filterRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Filter not found with id: " + id));
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

    public List<Filter> createFiltersFromDtos(List<FilterDto> filterDtos, Chat chat) {
        List<Filter> filters = new ArrayList<>();

        for (FilterDto dto : filterDtos) {
            Filter filter= new Filter();
            filter.setValue(dto.getValue());
            filter.setSummary(dto.getSummary());
            filter.setColor(dto.getColor());
            filter.setChat(chat);
            filters.add(filter);
        }

        return filters;
    }
}