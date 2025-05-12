package com.flash.finki.controller;

import com.flash.finki.model.DocumentUploadResponse;
import com.flash.finki.service.PDFTextExtractorService;
import com.flash.finki.service.QuestionGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

// METHOD NOT IN USE - DELETE LATER

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final PDFTextExtractorService pdfExtractorService;
    private final QuestionGenerationService questionGenerationService;

    private static final String UPLOAD_DIR = "uploads";

    @PostMapping("/process")
    @SneakyThrows
    public ResponseEntity<DocumentUploadResponse> processUploadedDocument(
            @RequestParam("filename") String filename) {
        File file = new File(UPLOAD_DIR, filename);
        if (!file.exists() || !file.isFile()) {
//            DocumentUploadResponse response = new DocumentUploadResponse();
//            response.setDocumentName("File not found: " + filename);
//            response.setTotalQuestions(0);
//            response.setQuestions(null);
//            return ResponseEntity.badRequest().body(response);
        }

        String extractedText = pdfExtractorService.extractTextFromPDF(file);

        var questions = questionGenerationService.generateQuestions(extractedText);

//        DocumentUploadResponse response = new DocumentUploadResponse();
//        response.setDocumentName(filename);
//        response.setQuestions(questions);
//        response.setTotalQuestions(questions.size());
//        System.out.println(response);

//        return ResponseEntity.ok(response);
        return null;
    }
}