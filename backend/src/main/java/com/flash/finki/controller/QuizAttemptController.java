package com.flash.finki.controller;

import com.flash.finki.model.QuizAttempt;
import com.flash.finki.model.dto.QuizAttemptAnswerDTO;
import com.flash.finki.model.dto.QuizAttemptDTO;
import com.flash.finki.model.dto.QuizAttemptSummaryDTO;
import com.flash.finki.model.dto.StartQuizAttemptRequestDTO;
import com.flash.finki.service.QuizAttemptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-attempts")
public class QuizAttemptController {

    private final QuizAttemptService attemptService;

    public QuizAttemptController(QuizAttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @PostMapping("/start")
    public ResponseEntity<QuizAttempt> startAttempt(@RequestBody StartQuizAttemptRequestDTO request) {
        return ResponseEntity.ok(attemptService.startNewAttempt(request.getQuizId(), request.getUserId()));
    }

    @GetMapping("/quiz/{quizId}/user/{userId}")
    public ResponseEntity<List<QuizAttemptSummaryDTO>> getAttempts(@PathVariable Long quizId, @PathVariable Long userId) {
        return ResponseEntity.ok(attemptService.getAttemptsForQuiz(quizId, userId));
    }

    @PostMapping("/{attemptId}/submit")
    public ResponseEntity<QuizAttemptDTO> submitAttempt(@PathVariable Long attemptId, @RequestBody List<QuizAttemptAnswerDTO> answers) {
        return ResponseEntity.ok(attemptService.submitAttempt(attemptId, answers));
    }
}
