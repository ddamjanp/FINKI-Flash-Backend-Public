package com.flash.finki.model.dto;

import com.flash.finki.model.AIOutput;
import com.flash.finki.model.File;
import com.flash.finki.model.User;
import jakarta.persistence.*;
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


