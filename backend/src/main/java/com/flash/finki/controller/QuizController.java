package com.flash.finki.controller;


import com.flash.finki.model.Quiz;
import com.flash.finki.model.dto.GenerateQuizRequestDTO;
import com.flash.finki.service.impl.QuizServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizServiceImpl quizService;

    @PostMapping("/generate")
    public ResponseEntity<Quiz> generateQuiz(
            @RequestBody GenerateQuizRequestDTO request
            )
    {
        Quiz quiz = quizService.createQuizFromFile(request.getFileId(), request.getUserId());
        return ResponseEntity.ok(quiz);
    }
}
