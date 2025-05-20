package com.flash.finki.service;

import com.flash.finki.model.Quiz;

public interface QuizService {

    Quiz createQuizFromFile(Long fileId, Long userId);

    boolean quizAlreadyExists(Long fileId, Long userId);
}
