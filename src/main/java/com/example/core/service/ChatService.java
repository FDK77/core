package com.example.core.service;

import com.example.core.dto.ChatDto;
import com.example.core.dto.ChatSettingDto;
import com.example.core.dto.FilterDto;
import com.example.core.models.Chat;
import com.example.core.models.Filter;
import com.example.core.models.Type;
import com.example.core.repo.ChatRepo;
import com.example.core.service.interfaces.IChatService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatService implements IChatService {
    private ChatRepo chatRepo;
    private FilterService filterService;
    private TelegramIntegrationService tgService;

    private ModelMapper modelMapper;
    @Autowired
    public ChatService(ChatRepo chatRepo, FilterService filterService, TelegramIntegrationService tgService, ModelMapper modelMapper) {
        this.chatRepo = chatRepo;
        this.filterService = filterService;
        this.tgService = tgService;
        this.modelMapper = modelMapper;

        this.modelMapper.typeMap(Chat.class, ChatDto.class)
                .addMappings(mapper -> {
                    mapper.map(Chat::getChatId, ChatDto::setChatId);
                    mapper.map(Chat::getTitle, ChatDto::setTitle);
                    mapper.map(Chat::getType, ChatDto::setType);
                });

        this.modelMapper.addConverter(context -> {
            Set<Filter> filters = context.getSource();
            return filters.stream()
                    .map(filter -> new FilterDto(
                            filter.getId(),
                            filter.getValue(),
                            filter.getSummary(),
                            filter.getColor(),
                            filter.getName()
                    ))
                    .collect(Collectors.toList());
        }, Set.class, List.class);
    }



    public void syncChatsWithTelegram() {
        List<Map<String, Object>> telegramChats = tgService.getAllChats();

        List<Long> incomingIds = telegramChats.stream()
                .map(chat -> ((Number) chat.get("chatId")).longValue())
                .toList();

        List<Chat> existingChats = chatRepo.findAll();

        List<Chat> chatsToDelete = existingChats.stream()
                .filter(chat -> !incomingIds.contains(chat.getChatId()))
                .toList();

        if (!chatsToDelete.isEmpty()) {
            chatRepo.deleteAll(chatsToDelete);
        }

        List<Chat> chatsToSave = new ArrayList<>();
        for (Map<String, Object> chatData : telegramChats) {
            Long chatId = ((Number) chatData.get("chatId")).longValue();
            String title = (String) chatData.get("title");
            String typeStr = (String) chatData.get("type");
            String avatar = (String) chatData.get("avatar");

            Chat chat = chatRepo.findById(chatId).orElseGet(() -> {
                Chat newChat = new Chat();
                newChat.setChatId(chatId);
                return newChat;
            });

            chat.setTitle(title);

            if (typeStr != null) {
                try {
                    chat.setType(Type.valueOf(typeStr.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    chat.setType(Type.UNKNOWN);
                }
            }
            chat.setAvatar(avatar);
            chatsToSave.add(chat);
        }

        if (!chatsToSave.isEmpty()) {
            chatRepo.saveAll(chatsToSave);
        }

    }

    public List<Long> getChatIdsWithFilters() {
        return chatRepo.findAllChatIdsWithFilters();
    }

    public void updateChatSettings(ChatSettingDto chatSettingDto) {
        chatRepo.findById(chatSettingDto.getChatId()).ifPresent(chat -> {
            Map<Long, Filter> existingFiltersMap = chat.getFilters().stream()
                    .filter(filter -> filter.getId() != null)
                    .collect(Collectors.toMap(Filter::getId, filter -> filter));

            Set<Filter> updatedFilters = new HashSet<>();

            for (FilterDto filterDto : chatSettingDto.getFilters()) {
                if (filterDto.getId() != null && existingFiltersMap.containsKey(filterDto.getId())) {
                    Filter existingFilter = existingFiltersMap.get(filterDto.getId());
                    existingFilter.setValue(filterDto.getValue());
                    existingFilter.setColor(filterDto.getColor());
                    existingFilter.setName(filterDto.getName());
                    existingFilter.setSummary(filterDto.getSummary());
                    updatedFilters.add(existingFilter);
                } else {
                    Filter newFilter = new Filter();
                    newFilter.setValue(filterDto.getValue());
                    newFilter.setColor(filterDto.getColor());
                    newFilter.setName(filterDto.getName());
                    newFilter.setSummary(filterDto.getSummary());
                    newFilter.setChat(chat);
                    updatedFilters.add(newFilter);
                }
            }

            chat.getFilters().clear();
            chat.getFilters().addAll(updatedFilters);
            chatRepo.save(chat);
        });

        List<Long> chatIdsWithFilters = getChatIdsWithFilters();
        tgService.subscribeChats(chatIdsWithFilters);
    }

    public List<Chat> getAllChats() {
        return chatRepo.findAll();
    }

    public List<ChatDto> getAllChatsDto() {
        List<Chat> chats = chatRepo.findAll();
        return chats.stream()
                .map(chat -> modelMapper.map(chat, ChatDto.class))
                .collect(Collectors.toList());
    }

    public Optional<Chat> getChatById(Long id) {
        return chatRepo.findById(id);
    }


    public Chat saveChat(Chat chat) {
        return chatRepo.save(chat);
    }

    public void deleteChat(Long id) {
        chatRepo.deleteById(id);
    }
}