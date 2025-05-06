package com.flash.finki.model.dto;

import lombok.Data;

@Data
public class GenerateQuizRequestDTO {
    private Long fileId;
    private Long userId;
}
