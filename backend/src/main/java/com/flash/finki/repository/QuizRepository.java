package com.flash.finki.repository;

import com.flash.finki.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    boolean existsByUserIdAndFileId(Long userId, Long fileId);
}