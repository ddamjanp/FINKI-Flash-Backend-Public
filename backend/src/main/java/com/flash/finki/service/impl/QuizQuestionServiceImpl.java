package com.flash.finki.service.impl;

import java.util.List;

import com.flash.finki.service.QuizQuestionService;
import org.springframework.stereotype.Service;

import com.flash.finki.model.QuizQuestion;
import com.flash.finki.repository.QuizQuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizQuestionServiceImpl implements QuizQuestionService {
    private final QuizQuestionRepository repo;

    public List<QuizQuestion> findByQuizId(Long quizId) {
        return repo.findByQuizId(quizId);
    }
}
