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
    @org.springframework.core.annotation.Order(1)
    CommandLineRunner initDatabase(StickerRepository stickerRepo, ThemeRepository themeRepo) {
        return args -> {
            // -- LIMPEZA DE DUPLICADOS POR ARQUIVO --
            // Se houver mais de um sticker apontando para o mesmo arquivo físico (ex: geralnacional.png),
            // removemos o duplicado.
            List<Sticker> todaBase = stickerRepo.findAll();
            java.util.Map<String, List<Sticker>> agrupaPorArquivo = todaBase.stream()
                    .filter(s -> s.getImageUrl() != null)
                    .collect(java.util.stream.Collectors.groupingBy(s -> {
                        try {
                            String url = s.getImageUrl();
                            String decoded = java.net.URLDecoder.decode(url, java.nio.charset.StandardCharsets.UTF_8);
                            return decoded.substring(decoded.lastIndexOf('/') + 1);
                        } catch (Exception e) {
                            return s.getImageUrl();
                        }
                    }));

            agrupaPorArquivo.forEach((arquivo, stickers) -> {
                if (stickers.size() > 1) {
                    System.out.println("⚠️ Limpando duplicados de arquivo para: " + arquivo);
                    // Procura se algum já tem URL do Cloudinary (prioridade)
                    Sticker manter = stickers.stream()
                            .filter(s -> s.getImageUrl().startsWith("http"))
                            .findFirst()
                            .orElse(stickers.get(0));

                    stickers.forEach(s -> {
                        if (!s.getId().equals(manter.getId())) {
                            stickerRepo.delete(s);
                            System.out.println("🗑️ Removido duplicado: ID=" + s.getId() + " (" + s.getName() + ")");
                        }
                    });
                }
            });

            // Garante que os temas existam
            themeRepo.findByName("Tradição")
                    .orElseGet(() -> themeRepo
                            .save(Theme.builder().name("Tradição").icon("🧉").colorHex("#9A6735").build()));

            themeRepo.findByName("Sangue Colorado")
                    .orElseGet(() -> themeRepo
                            .save(Theme.builder().name("Sangue Colorado").icon("🔴").colorHex("#D00000").build()));

            Theme imortal = themeRepo.findByName("Alma Imortal")
                    .orElseGet(() -> themeRepo
                            .save(Theme.builder().name("Alma Imortal").icon("🔵").colorHex("#0077B6").build()));

            themeRepo.findByName("Sulamerica")
                    .orElseGet(() -> themeRepo
                            .save(Theme.builder().name("Sulamerica").icon("🌎").colorHex("#2E7D32").build()));

            // Sincroniza a pasta de stickers no frontend com o banco de dados
            File stickersFolder = new File("../frontend/public/stickers");
            if (stickersFolder.exists() && stickersFolder.isDirectory()) {
                File[] files = stickersFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
                if (files != null) {
                    // Re-fetcha para garantir que os duplicados recém-removidos não estejam aqui
                    List<Sticker> baseFinal = stickerRepo.findAll();
                    List<String> filesInFolder = new ArrayList<>();

                    for (File file : files) {
                        String fileName = file.getName();
                        String imageUrl = "/stickers/" + fileName;
                        filesInFolder.add(imageUrl);

                        String baseName = fileName.replace(".png", "");
                        String displayName = formatName(baseName);

                        // Verificação ROBUSTA: compara o nome do arquivo, tratando URL Encoding (%20, etc)
                        boolean jaExiste = baseFinal.stream().anyMatch(s -> {
                            if (s.getImageUrl() == null) return false;
                            try {
                                String dbUrlDecoded = java.net.URLDecoder.decode(s.getImageUrl(), java.nio.charset.StandardCharsets.UTF_8);
                                return dbUrlDecoded.contains(fileName);
                            } catch (Exception e) {
                                return s.getImageUrl().contains(fileName);
                            }
                        });

                        if (!jaExiste) {
                            Sticker novo = stickerRepo.save(Sticker.builder()
                                    .name(displayName)
                                    .price(new BigDecimal("3.50"))
                                    .theme(imortal)
                                    .imageUrl(imageUrl)
                                    .build());
                            baseFinal.add(novo); // Adiciona à lista local para evitar duplicar no mesmo loop
                        }
                    }

                    // Atualiza o preço de TODOS os stickers existentes para 3.50 (Standard Price)
                    List<Sticker> listFinal = stickerRepo.findAll();
                    listFinal.forEach(s -> {
                       if (s.getPrice() == null || s.getPrice().compareTo(new BigDecimal("3.50")) != 0) {
                           s.setPrice(new BigDecimal("3.50"));
                           stickerRepo.save(s);
                       }
                    });

                    // Limpeza: remove do banco os stickers que não existem mais na pasta
                    // APENAS se o URL for local (/stickers/). URLs remotos (Cloudinary) são mantidos.
                    for (Sticker s : listFinal) {
                        String url = s.getImageUrl();
                        if (url != null && url.startsWith("/stickers/") && !filesInFolder.contains(url)) {
                            stickerRepo.delete(s);
                            System.out.println("🗑️ Removido sticker órfão do banco: " + s.getName());
                        }
                    }

                }
            } else {
                System.err.println("❌ Pasta de stickers não encontrada em: " + stickersFolder.getAbsolutePath());
            }

            System.out.println("✅ Banco de dados pronto!");
        };
    }

    private String formatName(String name) {
        if (name == null || name.isEmpty())
            return "Sticker";

        // Trata casos especiais como "acdc"
        if (name.equalsIgnoreCase("acdc"))
            return "AC/DC Mate";

        // Capitaliza a primeira letra e remove hífens/underscores
        String formatted = name.replace("_", " ").replace("-", " ");
        return formatted.substring(0, 1).toUpperCase() + formatted.substring(1);
    }
}
