package com.example.core.controller;

import com.example.core.dto.ReadFilterRequest;
import com.example.core.models.Filter;
import com.example.core.models.Message;
import com.example.core.repo.MessageRepo;
import com.example.core.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebSocketController {
    private final FilterService filterService;
    @Autowired
    public WebSocketController(FilterService filterService) {
        this.filterService = filterService;
    }
    @MessageMapping("/read")
    public void markMessagesAsRead(ReadFilterRequest request) {
       filterService.markAsCheckedByFilterId(request.getFilterId());
    }
}