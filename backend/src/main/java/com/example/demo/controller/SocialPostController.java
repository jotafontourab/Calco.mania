package com.example.demo.controller;

import com.example.demo.model.SocialPost;
import com.example.demo.repository.SocialPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SocialPostController {

    private final SocialPostRepository socialPostRepository;

    @GetMapping
    public List<SocialPost> getGallery() {
        return socialPostRepository.findAllByOrderByCreatedAtDesc();
    }

    @PostMapping
    public ResponseEntity<SocialPost> createPost(@RequestBody SocialPost post) {
        return ResponseEntity.ok(socialPostRepository.save(post));
    }
}
