package com.example.core.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String TELEGRAM_TO_CORE_QUEUE = "telegram.to_core";

    @Bean
    public Queue telegramToCoreQueue() {
        return new Queue(TELEGRAM_TO_CORE_QUEUE, false);
    }
}
