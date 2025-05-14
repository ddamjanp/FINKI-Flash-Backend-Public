package com.flash.finki.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.flash.finki.service.PDFExportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/export")
@RequiredArgsConstructor
public class PDFExportController {

    private final PDFExportService pdfExportService;

    @GetMapping(value = "/quiz/{quizId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> downloadQuiz(@PathVariable Long quizId) {
        ByteArrayInputStream bis = pdfExportService.exportQuiz(quizId);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=quiz_" + quizId + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}