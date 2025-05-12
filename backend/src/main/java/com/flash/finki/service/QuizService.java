package com.flash.finki.service;

import com.flash.finki.model.Quiz;

public interface QuizService {

    public Quiz createQuizFromFile(Long fileId, Long userId);

    public boolean quizAlreadyExists(Long fileId, Long userId);
}
