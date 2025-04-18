package com.example.core.service.interfaces;

import com.example.core.models.Filter;

import java.util.List;
import java.util.Optional;

public interface IFilterService {

    List<Filter> getAllFilters();

    Optional<Filter> getFilterById(Long id);

    Filter saveFilter(Filter filter);

   void deleteFilter(Long id);

}
