package com.flash.finki.controller;

import com.flash.finki.model.Quiz;
import com.flash.finki.model.dto.GenerateQuizRequestDTO;
import com.flash.finki.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/generate")
    public ResponseEntity<Quiz> generateQuiz(
            @RequestBody GenerateQuizRequestDTO request
            )
    {
        Quiz quiz = quizService.createQuizFromFile(request.getFileId(), request.getUserId());
        return ResponseEntity.ok(quiz);
    }
}
