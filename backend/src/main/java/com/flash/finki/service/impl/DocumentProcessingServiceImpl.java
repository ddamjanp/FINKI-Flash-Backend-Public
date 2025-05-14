package com.flash.finki.service.impl;

import com.flash.finki.model.AIOutput;
import com.flash.finki.model.DocumentUploadResponse;
import com.flash.finki.model.File;
import com.flash.finki.repository.AIOutputRepository;
import com.flash.finki.repository.FileRepository;
import com.flash.finki.service.DocumentProcessingService;
import com.flash.finki.service.QuestionGenerationService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentProcessingServiceImpl implements DocumentProcessingService {

    private final QuestionGenerationService questionGenerationService;
    private final AIOutputRepository aiOutputRepository;
    private final FileRepository fileRepository;

    @Override
    public DocumentUploadResponse processUploadedText(File dbFile, String extractedString) {
        List<AIOutput> outputs = questionGenerationService. generateQuestions(extractedString);

        outputs.forEach(output -> {
            output.setFile(dbFile);
            output.setCreatedAt(LocalDateTime.now());
        });

        aiOutputRepository.saveAll(outputs);

        return new DocumentUploadResponse(
                dbFile.getFilename(),
                outputs.size(),
                outputs
        );
    }

    @Override
    public String extractTextFromPDF(InputStream inputStream) {
        try (PDDocument document = PDDocument.load(inputStream)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            return text.length() > 4000 ? text.substring(0, 4000) : text;
        } catch (Exception e) {
            return "";
        }
    }
}
