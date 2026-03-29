package com.example.demo.service;

import com.example.demo.model.Sticker;
import com.example.demo.repository.StickerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;

@Order(2)
@Component
public class CloudinaryMigrationRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CloudinaryMigrationRunner.class);
    private final StickerRepository stickerRepository;
    private final CloudinaryService cloudinaryService;

    // Path to your frontend public folder (relative to the backend root)
    private static final String STICKERS_PATH = "../frontend/public/stickers/";

    public CloudinaryMigrationRunner(StickerRepository stickerRepository, CloudinaryService cloudinaryService) {
        this.stickerRepository = stickerRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Checking for images to migrate to Cloudinary...");
        
        List<Sticker> stickers = stickerRepository.findAll();
        int migratedCount = 0;

        for (Sticker sticker : stickers) {
            String url = sticker.getImageUrl();
            
            // Check if the URL is local (doesn't start with http or is relative)
            if (url != null && !url.startsWith("http")) {
                // If it's just a filename, prepend the path
                String fileName = url;
                if (url.startsWith("/stickers/")) {
                    fileName = url.substring(10);
                } else if (url.startsWith("stickers/")) {
                    fileName = url.substring(9);
                }
                
                File imageFile = new File(STICKERS_PATH + fileName);
                
                if (imageFile.exists()) {
                    try {
                        logger.info("Migrating sticker '{}' (File: {}) to Cloudinary...", sticker.getName(), fileName);
                        Map result = cloudinaryService.upload(imageFile);
                        String cloudinaryUrl = (String) result.get("secure_url");
                        
                        sticker.setImageUrl(cloudinaryUrl);
                        stickerRepository.save(sticker);
                        
                        logger.info("Successfully migrated sticker '{}'. New URL: {}", sticker.getName(), cloudinaryUrl);
                        migratedCount++;
                    } catch (Exception e) {
                        logger.error("Failed to migrate sticker '{}': {}", sticker.getName(), e.getMessage());
                    }
                } else {
                    logger.warn("Local file not found for sticker '{}' at path: {}", sticker.getName(), imageFile.getAbsolutePath());
                }
            }
        }

        if (migratedCount > 0) {
            logger.info("Migration finished! {} stickers migrated to Cloudinary.", migratedCount);
        } else {
            logger.info("No stickers needed migration or no credentials set.");
        }
    }
}
