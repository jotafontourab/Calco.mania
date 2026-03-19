package com.example.demo.service;

import com.example.demo.model.Sticker;
import com.example.demo.repository.StickerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StickerService {
    private final StickerRepository stickerRepository;

    public StickerService(StickerRepository stickerRepository) {
        this.stickerRepository = stickerRepository;
    }

    public List<Sticker> findAll() {
        return stickerRepository.findAll();
    }

    public List<Sticker> findByTheme(Long themeId) {
        return stickerRepository.findByThemeId(themeId);
    }
}
