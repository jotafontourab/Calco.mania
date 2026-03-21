package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "social_posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String username;

    private String caption;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
