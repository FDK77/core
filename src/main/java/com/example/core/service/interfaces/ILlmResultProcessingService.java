package com.example.core.service.interfaces;

import com.example.core.dto.LlmResultMessageDto;

import java.util.List;

public interface ILlmResultProcessingService {
    void processLlmResults(List<LlmResultMessageDto> messages);
}
