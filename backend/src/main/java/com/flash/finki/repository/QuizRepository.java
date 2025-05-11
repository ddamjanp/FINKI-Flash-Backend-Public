package com.flash.finki.repository;

import com.flash.finki.model.Quiz;
import com.flash.finki.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByUser(User user);

    boolean existsByUserIdAndFileId(Long userId, Long fileId);
}