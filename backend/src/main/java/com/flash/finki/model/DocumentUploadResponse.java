package com.flash.finki.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DocumentUploadResponse {

    private String documentName;

    private int totalQuestions;

    private List<AIOutput> questions;
}
