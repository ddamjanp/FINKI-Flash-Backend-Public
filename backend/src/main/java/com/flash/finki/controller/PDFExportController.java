package com.flash.finki.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.flash.finki.model.QuizQuestion;
import com.flash.finki.service.PDFExportService;
import com.flash.finki.service.QuizQuestionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/export")
@RequiredArgsConstructor
public class PDFExportController {
    
    private final PDFExportService pdfExportService;
    private final QuizQuestionService questionService; 

    @GetMapping(value = "/flashcards", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> downloadFlashcards() {
        List<QuizQuestion> questions = questionService.findFirst5();
        ByteArrayInputStream bis = pdfExportService.exportFlashcards(questions);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=flashcards.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping(value = "/quizzes", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> downloadQuizzes() {
        List<QuizQuestion> questions = questionService.findFirst5();
        ByteArrayInputStream bis = pdfExportService.exportQuizzes(questions);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=quiz.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}