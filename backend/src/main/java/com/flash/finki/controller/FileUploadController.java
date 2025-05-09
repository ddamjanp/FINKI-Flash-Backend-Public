package com.flash.finki.controller;

import com.flash.finki.model.DocumentUploadResponse;
import com.flash.finki.model.File;
import com.flash.finki.model.User;
import com.flash.finki.repository.FileRepository;
import com.flash.finki.repository.UserRepository;
import com.flash.finki.service.DocumentProcessingService;
import com.flash.finki.service.PDFTextExtractorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final PDFTextExtractorService pdfExtractorService;
    private final DocumentProcessingService documentProcessingService;

    @PostMapping("/upload")
    @Transactional
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId) throws IOException {

        String fileName = file.getOriginalFilename();
        if (!isValidFileExtension(fileName)) {
            return ResponseEntity.badRequest().body("Invalid file type");
        }

        String sanitizedFileName = fileName.replaceAll("[^a-zA-Z0-9.-]", "_");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        File dbFile = new File();
        dbFile.setFilename(sanitizedFileName);
        dbFile.setUser(user);
        dbFile.setFileType(resolveFileType(sanitizedFileName));
        dbFile.setUploadedAt(LocalDateTime.now());
        dbFile.setFileUrl("IN_MEMORY_UPLOAD");

        fileRepository.save(dbFile);

        return ResponseEntity.ok("File metadata saved successfully.");
    }

    @PostMapping("/process")
    public ResponseEntity<DocumentUploadResponse> processFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("filename") String filename) throws IOException {

        // For security
        File dbFile = fileRepository.findByFilename(filename)
                .orElseThrow(() -> new IllegalArgumentException("File not found in DB"));

        String extractedText = documentProcessingService.extractTextFromPDF(file.getInputStream());
        DocumentUploadResponse response = documentProcessingService.processUploadedText(dbFile, extractedText);

        return ResponseEntity.ok(response);
    }

    private boolean isValidFileExtension(String fileName) {
        String fileExtension = fileName != null ? getFileExtension(fileName) : "";
        return fileExtension.equalsIgnoreCase("pdf") || fileExtension.equalsIgnoreCase("csv") || fileExtension.equalsIgnoreCase("txt");
    }

    private String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        return index > 0 ? fileName.substring(index + 1) : "";
    }

    private File.FileType resolveFileType(String fileName) {
        String extension = getFileExtension(fileName).toLowerCase();
        return switch (extension) {
            case "pdf" -> File.FileType.PDF;
            case "docx" -> File.FileType.DOCX;
            case "txt" -> File.FileType.TXT;
            default -> throw new IllegalArgumentException("Unsupported file type: " + extension);
        };
    }
}