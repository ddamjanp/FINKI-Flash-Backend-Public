package com.flash.finki.controller;

import com.flash.finki.model.Quiz;
import com.flash.finki.model.dto.GenerateQuizRequestDTO;
import com.flash.finki.service.impl.QuizServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizServiceImpl quizService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateQuiz(
            @RequestBody GenerateQuizRequestDTO request
            )
    {
        if (quizService.quizAlreadyExists(request.getFileId(), request.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Quiz already exists for this file and user.");
        }
        Quiz quiz = quizService.createQuizFromFile(request.getFileId(), request.getUserId());
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> quizExists(@RequestParam Long fileId, @RequestParam Long userId) {
        boolean exists = quizService.quizAlreadyExists(fileId, userId);
        return ResponseEntity.ok(exists);
    }

}
