package com.flash.finki.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileUploadController {

    private static final String UPLOAD_DIR = "uploads";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (!isValidFileExtension(fileName)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid file type. Only PDF, CSV, and TXT files are allowed.");
        }

        String sanitizedFileName = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9.-]", "_");

        try {
            Path uploadDirectory = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadDirectory)) {
                Files.createDirectories(uploadDirectory);
            }

            Path targetLocation = uploadDirectory.resolve(sanitizedFileName);
            uploadFileWithProgress(file, targetLocation);

            return ResponseEntity.ok("File uploaded successfully: " + sanitizedFileName);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }

    private boolean isValidFileExtension(String fileName) {
        String fileExtension = fileName != null ? getFileExtension(fileName) : "";
        return fileExtension.equalsIgnoreCase("pdf") || fileExtension.equalsIgnoreCase("csv") || fileExtension.equalsIgnoreCase("txt");
    }

    private String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        return index > 0 ? fileName.substring(index + 1) : "";
    }

    private void uploadFileWithProgress(MultipartFile file, Path targetLocation) throws IOException {
        byte[] buffer = new byte[4096]; // Buffer for reading the file
        long totalBytes = file.getSize();
        long bytesRead = 0;

        try (var inputStream = file.getInputStream()) {
            try (var outputStream = Files.newOutputStream(targetLocation)) {
                int read;
                while ((read = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                    bytesRead += read;

                    double progress = ((double) bytesRead / totalBytes) * 100;
                    System.out.println("Upload Progress: " + String.format("%.2f", progress) + "%");
                }
            }
        }
    }
}
