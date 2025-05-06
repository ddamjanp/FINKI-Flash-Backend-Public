package com.flash.finki.controller;


import com.flash.finki.model.Quiz;
import com.flash.finki.service.impl.QuizServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizServiceImpl quizService;

    @PostMapping("/generate")
    public ResponseEntity<Quiz> generateQuiz(
            @RequestParam Long fileId,
            @RequestParam Long userId
    )
    {
        Quiz quiz = quizService.createQuizFromFile(fileId,userId);
        return ResponseEntity.ok(quiz);
    }
}
