package com.flash.finki.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class QuizAttemptSummaryDTO {

    private Long attemptId;

    private String quizTitle;

    private int score;

    private LocalDateTime attemptedAt;
}
