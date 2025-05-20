package com.flash.finki.controller;

import com.flash.finki.model.AIOutput;
import com.flash.finki.model.QuizAttempt;
import com.flash.finki.model.QuizQuestion;
import com.flash.finki.model.dto.*;
import com.flash.finki.repository.QuizQuestionRepository;
import com.flash.finki.service.AIOutputService;
import com.flash.finki.service.QuizAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-attempts")
@RequiredArgsConstructor
public class QuizAttemptController {
    private final QuizAttemptService attemptService;
    private final AIOutputService aiOutputService;
    private final QuizQuestionRepository quizQuestionRepository;

    @PostMapping("/start")
    public ResponseEntity<StartQuizAttemptResponseDTO> startAttempt(@RequestBody StartQuizAttemptRequestDTO request) {
        QuizAttempt attempt = attemptService.startNewAttempt(request.getQuizId(), request.getUserId());

        List<QuizQuestion> quizQuestions = quizQuestionRepository.findAllByQuizId(attempt.getQuiz().getId());
        List<AIOutput> aiOutputs = quizQuestions.stream()
                .map(QuizQuestion::getAiOutput)
                .toList();

        List<Long> quizQuestionIds = quizQuestions.stream()
                .map(QuizQuestion::getId)
                .toList();

        StartQuizAttemptResponseDTO response = new StartQuizAttemptResponseDTO(
                attempt.getId(),
                aiOutputs,
                quizQuestionIds
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/quiz/{quizId}/user/{userId}")
    public ResponseEntity<List<QuizAttemptSummaryDTO>> getAttempts(@PathVariable Long quizId, @PathVariable Long userId) {
        return ResponseEntity.ok(attemptService.getAttemptsForQuiz(quizId, userId));
    }

    @PostMapping("/{attemptId}/submit")
    public ResponseEntity<QuizAttemptDTO> submitAttempt(@PathVariable Long attemptId,
                                                        @RequestBody List<QuizAttemptAnswerDTO> answers) {
        return ResponseEntity.ok(attemptService.submitAttempt(attemptId, answers));
    }
}
