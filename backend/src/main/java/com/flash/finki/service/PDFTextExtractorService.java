package com.flash.finki.service;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class PDFTextExtractorService {
    @SneakyThrows
    public String extractTextFromPDF(File file) {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            return text.length() > 4000 ? text.substring(0, 4000) : text;
        }
    }
}
