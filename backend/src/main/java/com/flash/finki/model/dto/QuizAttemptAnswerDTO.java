package com.flash.finki.model.dto;

import lombok.Data;

@Data
public class QuizAttemptAnswerDTO {

    private Long quizQuestionId;

    private String selectedAnswer;

    private boolean isCorrect;
}

