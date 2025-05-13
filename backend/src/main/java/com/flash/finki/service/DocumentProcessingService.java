package com.flash.finki.service;

import com.flash.finki.model.DocumentUploadResponse;
import com.flash.finki.model.File;

import java.io.InputStream;

public interface DocumentProcessingService {

    public DocumentUploadResponse processUploadedText(File dbFile, String extractedString);

    public String extractTextFromPDF(InputStream inputStream);
}
