package com.flash.finki.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuizAttemptDTO {
    private Long id;
    private Long quizId;
    private Long userId;
    private LocalDateTime attemptedAt;
    private Integer score;
    private List<QuizAttemptAnswerDTO> answers;
}
