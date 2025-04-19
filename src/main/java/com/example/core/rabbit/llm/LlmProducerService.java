package com.example.core.rabbit.llm;

import com.example.core.config.RabbitConfig;
import com.example.core.dto.LlmRequestDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LlmProducerService {
    private RabbitTemplate rabbitTemplate;
    @Autowired
    public LlmProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToLlm(LlmRequestDto request) {
        rabbitTemplate.convertAndSend(RabbitConfig.CORE_TO_LLM_QUEUE, request);
    }
}