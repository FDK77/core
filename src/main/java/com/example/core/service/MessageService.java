package com.example.core.service;


import com.example.core.dto.FilterToLlmDto;
import com.example.core.dto.MessageDto;
import com.example.core.models.Message;
import com.example.core.repo.MessageRepo;
import com.example.core.service.interfaces.IMessageService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessageService implements IMessageService {
    private MessageRepo messageRepo;
    private FilterService filterService;
    private ModelMapper modelMapper;
    @Autowired
    public MessageService(MessageRepo messageRepo, FilterService filterService, ModelMapper modelMapper) {
        this.messageRepo = messageRepo;
        this.filterService = filterService;
        this.modelMapper = modelMapper;
    }

    public List<MessageDto> getAllMessagesDtoByChatId(Long chatId) {
        return messageRepo.findAllByChatId(chatId).stream()
                .map(message -> new MessageDto(
                        message.getId(),
                        message.getText(),
                        message.getSummary(),
                        message.getTimestamp(),
                        message.getSender()
                ))
                .toList();
    }

    public List<MessageDto> getAllMessagesDtoByFilterId(Long filterId) {
        List<Message> messages = messageRepo.findAllByFilterId(filterId);
        List<MessageDto> massageDto = messages.stream()
                .map(m -> modelMapper.map(m, MessageDto.class))
                .collect(Collectors.toList());
        return massageDto;
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
    @Transactional
    public void deleteAllMessagesForFilter(Long filterId) {
        try {
            filterService.findById(filterId);
            messageRepo.deleteAllByFilterId(filterId);
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("Filter with ID " + filterId + " not found");
        }
    }
}
