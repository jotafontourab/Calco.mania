package com.example.demo.controller;

import com.example.demo.model.Theme;
import com.example.demo.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/themes")
public class ThemeController {
    
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping
    public ResponseEntity<List<Theme>> getAllThemes() {
        return ResponseEntity.ok(themeService.findAll());
    }
}
