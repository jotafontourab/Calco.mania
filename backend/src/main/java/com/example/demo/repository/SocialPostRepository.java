package com.example.demo.repository;

import com.example.demo.model.SocialPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialPostRepository extends JpaRepository<SocialPost, Long> {
    List<SocialPost> findAllByOrderByCreatedAtDesc();
}
