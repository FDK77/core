package com.example.core.controller;

import com.example.core.dto.LlmResultMessageDto;
import com.example.core.service.LlmResultProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/llm/results")
public class LlmResultController {

    private LlmResultProcessingService processingService;
    @Autowired
    public LlmResultController(LlmResultProcessingService processingService) {
        this.processingService = processingService;
    }

    @PostMapping
    public ResponseEntity<Void> receiveLlmResults(@RequestBody List<LlmResultMessageDto> messages) {
        processingService.processLlmResults(messages);
        return ResponseEntity.ok().build();
    }
}
