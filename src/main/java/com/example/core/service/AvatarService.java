package com.example.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;

@Service
public class AvatarService {
    private final RestTemplate restTemplate;

    @Value("${telegram.service.base-url}")
    private String telegramBaseUrl;

    @Value("${avatar.storage.path}")
    private String avatarStoragePath;

    @Autowired
    public AvatarService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void downloadAndSaveChatAvatar(Long chatId) {
        downloadAndSaveFromUrl("/avatar/chat/" + chatId, chatId);
    }

    public void downloadAndSaveUserAvatar(Long userId) {
        downloadAndSaveFromUrl("/avatar/user/" + userId, userId);
    }

    private void downloadAndSaveFromUrl(String path, Long id) {
        try {
            File dir = new File(avatarStoragePath);
            if (!dir.exists()) dir.mkdirs();

            File target = new File(dir, id + ".jpg");
            if (target.exists()) return;

            String url = telegramBaseUrl + path;
            UrlResource resource = new UrlResource(new URI(url));

            try (InputStream in = resource.getInputStream();
                 FileOutputStream out = new FileOutputStream(target)) {
                FileCopyUtils.copy(in, out);
                System.out.println("Аватар сохранён: " + target.getAbsolutePath());
            }

        } catch (Exception e) {
            System.err.println("Не удалось скачать аватар по пути " + path + ": " + e.getMessage());
        }
    }
}
