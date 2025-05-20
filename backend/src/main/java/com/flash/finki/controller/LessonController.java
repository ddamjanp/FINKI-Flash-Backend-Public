package com.flash.finki.controller;

import com.flash.finki.model.File;
import com.flash.finki.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Lessons")
public class LessonController {

    private final FileService fileService;

    public LessonController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<File>> getAllByUserId(@PathVariable Long userId) {
        List<File> files = fileService.getAllByUserId(userId);
        System.out.println("Fetched files for user " + userId + ": " + files.size() + " files found.");
        return ResponseEntity.ok(files);
    }
}
