package com.flash.finki.service;

import com.flash.finki.model.QuizAttempt;
import com.flash.finki.model.QuizAttemptAnswer;
import com.flash.finki.model.dto.QuizAttemptAnswerDTO;
import com.flash.finki.model.dto.QuizAttemptDTO;
import com.flash.finki.model.dto.QuizAttemptSummaryDTO;

import java.util.List;

public interface QuizAttemptService {

    public QuizAttempt startNewAttempt(Long quizId, Long userId);

    public List<QuizAttemptSummaryDTO> getAttemptsForQuiz(Long quizId, Long userId);

    public QuizAttemptDTO submitAttempt(Long attemptId, List<QuizAttemptAnswerDTO> submittedAnswers);
}
