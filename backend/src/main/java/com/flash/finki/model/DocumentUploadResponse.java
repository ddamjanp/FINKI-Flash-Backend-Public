package com.flash.finki.model;

import lombok.Data;

import java.util.List;

@Data
public class DocumentUploadResponse {
    private String documentName;
    private int totalQuestions;
    private List<QuizQuestion> questions;
}
