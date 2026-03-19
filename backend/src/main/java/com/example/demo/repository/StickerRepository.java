package com.example.demo.repository;

import com.example.demo.model.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StickerRepository extends JpaRepository<Sticker, Long> {
    List<Sticker> findByThemeId(Long themeId);
}
