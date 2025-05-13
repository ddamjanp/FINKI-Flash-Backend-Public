package com.flash.finki.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FlashcardDTO {

    private Long id;

    private String question;

    private String correctAnswer;

    private LocalDateTime createdAt;
}


