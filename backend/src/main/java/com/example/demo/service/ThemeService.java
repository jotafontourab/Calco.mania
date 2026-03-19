package com.example.demo.service;

import com.example.demo.model.Theme;
import com.example.demo.repository.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public List<Theme> findAll() {
        return themeRepository.findAll();
    }
}
