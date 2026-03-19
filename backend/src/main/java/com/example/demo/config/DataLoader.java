package com.example.demo.config;

import com.example.demo.model.Sticker;
import com.example.demo.model.Theme;
import com.example.demo.repository.StickerRepository;
import com.example.demo.repository.ThemeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(StickerRepository stickerRepo, ThemeRepository themeRepo) {
        return args -> {
            // Garante que os temas existam
            themeRepo.findByName("Tradição & Mate")
                    .orElseGet(() -> themeRepo
                            .save(Theme.builder().name("Tradição & Mate").icon("🌿").colorHex("#9A6735").build()));

            themeRepo.findByName("Sangue Colorado")
                    .orElseGet(() -> themeRepo
                            .save(Theme.builder().name("Sangue Colorado").icon("🔴").colorHex("#D00000").build()));

            Theme imortal = themeRepo.findByName("Alma Imortal")
                    .orElseGet(() -> themeRepo
                            .save(Theme.builder().name("Alma Imortal").icon("🔵").colorHex("#0077B6").build()));

            // Limpa adesivos antigos para sincronizar com a pasta física
            stickerRepo.deleteAll();

            // Scurre a pasta de stickers no frontend
            File stickersFolder = new File("../frontend/public/stickers");
            List<Sticker> dynamicStickers = new ArrayList<>();

            if (stickersFolder.exists() && stickersFolder.isDirectory()) {
                File[] files = stickersFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
                if (files != null) {
                    for (File file : files) {
                        String fileName = file.getName();
                        String baseName = fileName.replace(".png", "");
                        
                        // Nome formatado (ex: "geromel" -> "Geromel")
                        String displayName = formatName(baseName);

                        dynamicStickers.add(Sticker.builder()
                                .name(displayName)
                                .price(new BigDecimal("5.00"))
                                .theme(imortal)
                                .imageUrl("/stickers/" + fileName)
                                .build());
                    }
                }
                stickerRepo.saveAll(dynamicStickers);
                System.out.println("✅ " + dynamicStickers.size() + " Stickers carregados dinamicamente da pasta!");
            } else {
                System.err.println("❌ Pasta de stickers não encontrada em: " + stickersFolder.getAbsolutePath());
            }

            System.out.println("✅ Banco de dados pronto!");
        };
    }

    private String formatName(String name) {
        if (name == null || name.isEmpty()) return "Sticker";
        
        // Trata casos especiais como "acdc"
        if (name.equalsIgnoreCase("acdc")) return "AC/DC Mate";
        
        // Capitaliza a primeira letra e remove hífens/underscores
        String formatted = name.replace("_", " ").replace("-", " ");
        return formatted.substring(0, 1).toUpperCase() + formatted.substring(1);
    }
}
