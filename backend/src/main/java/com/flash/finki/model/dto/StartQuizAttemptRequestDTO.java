package com.flash.finki.model.dto;

import lombok.Data;

@Data
public class StartQuizAttemptRequestDTO {

    private Long quizId;

    private Long userId;
}
