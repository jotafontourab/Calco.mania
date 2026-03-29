package com.example.demo.controller;

import com.example.demo.model.Sticker;
import com.example.demo.service.StickerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stickers")
public class StickerController {
    
    private final StickerService stickerService;

    public StickerController(StickerService stickerService) {
        this.stickerService = stickerService;
    }

    @GetMapping
    public ResponseEntity<List<Sticker>> getAllStickers(@RequestParam(required = false) Long themeId) {
        if (themeId != null) {
            return ResponseEntity.ok(stickerService.findByTheme(themeId));
        }
        return ResponseEntity.ok(stickerService.findAll());
    }
}
