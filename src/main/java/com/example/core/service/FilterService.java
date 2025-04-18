package com.example.core.service;

import com.example.core.models.Filter;
import com.example.core.repo.FilterRepo;
import com.example.core.service.interfaces.IFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilterService implements IFilterService {
    private FilterRepo filterRepo;

    @Autowired
    public FilterService(FilterRepo filterRepo) {
        this.filterRepo = filterRepo;
    }

    public List<Filter> getAllFilters() {
        return filterRepo.findAll();
    }

    public Optional<Filter> getFilterById(Long id) {
        return filterRepo.findById(id);
    }

    public Filter saveFilter(Filter filter) {
        return filterRepo.save(filter);
    }

    public void deleteFilter(Long id) {
        filterRepo.deleteById(id);
    }
}