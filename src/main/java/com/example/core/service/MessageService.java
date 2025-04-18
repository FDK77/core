package com.example.core.service;


import com.example.core.dto.MessageDto;
import com.example.core.models.Message;
import com.example.core.repo.MessageRepo;
import com.example.core.service.interfaces.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService implements IMessageService {
    private MessageRepo messageRepo;

    @Autowired
    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public List<MessageDto> getAllMessagesDtoByChatId(Long chatId) {
        return messageRepo.findAllByChatId(chatId).stream()
                .map(message -> new MessageDto(
                        message.getId(),
                        message.getText(),
                        message.getTimestamp(),
                        message.getSender()
                ))
                .toList();
    }


    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }

    public Optional<Message> getMessageById(Long id) {
        return messageRepo.findById(id);
    }

    public Message saveMessage(Message message) {
        return messageRepo.save(message);
    }

    public void deleteMessage(Long id) {
        messageRepo.deleteById(id);
    }
}
