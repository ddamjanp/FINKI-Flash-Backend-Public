package com.flash.finki.service;

import com.flash.finki.model.QuizQuestion;

import java.util.List;

public interface QuizQuestionService {

    List<QuizQuestion> findByQuizId(Long quizId);
}
