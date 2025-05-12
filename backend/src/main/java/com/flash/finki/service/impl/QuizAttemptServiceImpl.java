package com.flash.finki.service.impl;

import com.flash.finki.model.*;
import com.flash.finki.model.dto.QuizAttemptAnswerDTO;
import com.flash.finki.model.dto.QuizAttemptDTO;
import com.flash.finki.model.dto.QuizAttemptSummaryDTO;
import com.flash.finki.repository.*;
import com.flash.finki.service.QuizAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuizAttemptServiceImpl implements QuizAttemptService {

    private final QuizAttemptRepository attemptRepository;
    private final QuizQuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuizAttemptAnswerRepository answerRepository;

    @Override
    public QuizAttempt startNewAttempt(Long quizId, Long userId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        QuizAttempt attempt = new QuizAttempt();
        attempt.setQuiz(quiz);
        attempt.setUser(user);
        attempt.setAttemptedAt(LocalDateTime.now());
        attempt.setScore(0);
        attempt.setAnswers(new ArrayList<>());

        return attemptRepository.save(attempt);
    }

    @Override
    public List<QuizAttemptSummaryDTO> getAttemptsForQuiz(Long quizId, Long userId) {
        List<QuizAttempt> quizAttempts = attemptRepository.findByQuizIdAndUserId(quizId, userId);

        return quizAttempts.stream()
                .map(attempt -> new QuizAttemptSummaryDTO(
                        attempt.getId(),
                        attempt.getQuiz().getTitle(),
                        attempt.getScore(),
                        attempt.getAttemptedAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public QuizAttemptDTO submitAttempt(Long attemptId, List<QuizAttemptAnswerDTO> submittedAnswers) {
        QuizAttempt attempt = attemptRepository.    findById(attemptId)
                .orElseThrow(() -> new IllegalArgumentException("Attempt not found"));

        int total = 0;
        int correct = 0;
        List<QuizAttemptAnswer> savedAnswers = new ArrayList<>();

        for (QuizAttemptAnswerDTO submitted : submittedAnswers) {
            QuizQuestion question = questionRepository.findById(submitted.getQuizQuestionId())
                    .orElseThrow(() -> new IllegalArgumentException("Question not found"));

            boolean isCorrect = submitted.getSelectedAnswer().equals(question.getAiOutput().getCorrectAnswer());

            QuizAttemptAnswer answer = new QuizAttemptAnswer();
            answer.setAttempt(attempt);
            answer.setQuizQuestion(question);
            answer.setSelectedAnswer(submitted.getSelectedAnswer());
            answer.setCorrect(isCorrect);
            answerRepository.save(answer);

            savedAnswers.add(answer);
            total++;
            if (isCorrect) correct++;
        }

        int score = total > 0 ? (correct * 100 / total) : 0;
        attempt.setScore(score);
        attempt.setAnswers(savedAnswers);
        QuizAttempt saved = attemptRepository.save(attempt);

        // Map back to DTO
        QuizAttemptDTO dto = new QuizAttemptDTO();
        dto.setId(saved.getId());
        dto.setQuizId(saved.getQuiz().getId());
        dto.setUserId(saved.getUser().getId());
        dto.setAttemptedAt(saved.getAttemptedAt());
        dto.setScore(saved.getScore());

        List<QuizAttemptAnswerDTO> dtoAnswers = savedAnswers.stream().map(a -> {
            QuizAttemptAnswerDTO dtoA = new QuizAttemptAnswerDTO();
            dtoA.setQuizQuestionId(a.getQuizQuestion().getId());
            dtoA.setSelectedAnswer(a.getSelectedAnswer());
            return dtoA;
        }).toList();

        dto.setAnswers(dtoAnswers);
        return dto;
    }
}
