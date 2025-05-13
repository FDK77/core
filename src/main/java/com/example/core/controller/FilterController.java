package com.example.core.controller;

import com.example.core.dto.ChatDto;
import com.example.core.dto.FilterDto;
import com.example.core.models.Filter;
import com.example.core.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/filters")
public class FilterController {

    private FilterService filterService;
    @Autowired
    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<FilterDto>> getFiltersByChatId(@PathVariable Long id) {
        return ResponseEntity.ok(filterService.getFilterDtosByChatId(id));
    }
}
