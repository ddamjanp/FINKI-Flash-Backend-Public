package com.flash.finki.service;

import com.flash.finki.model.DocumentUploadResponse;
import com.flash.finki.model.File;

import java.io.InputStream;

public interface DocumentProcessingService {

    DocumentUploadResponse processUploadedText(File dbFile, String extractedString);

    String extractTextFromPDF(InputStream inputStream);
}
