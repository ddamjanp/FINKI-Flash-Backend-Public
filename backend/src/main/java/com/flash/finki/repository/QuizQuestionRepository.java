package com.flash.finki.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.flash.finki.model.QuizQuestion;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion,Long> {
    List<QuizQuestion> findAllBy(Pageable pageable);
}
