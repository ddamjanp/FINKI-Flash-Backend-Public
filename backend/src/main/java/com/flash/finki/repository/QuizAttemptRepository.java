package com.flash.finki.repository;

import com.flash.finki.model.QuizAttempt;
import com.flash.finki.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    List<QuizAttempt> findByUser(User user);

    List<QuizAttempt> findByQuizId(Long quizId);

    List<QuizAttempt> findByQuizIdAndUserId(Long quizId, Long userid);

}