package com.flash.finki.service.impl;

import com.flash.finki.model.*;
import com.flash.finki.repository.*;
import com.flash.finki.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final AIOutputRepository aiOutputRepository;
    private final QuizRepository quizRepository;
    private final QuizQuestionRepository quizQuestionRepository;

    @Override
    public Quiz createQuizFromFile(Long fileId, Long userId) {

        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("File not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<AIOutput> aiOutputs = aiOutputRepository.findByFileId(fileId);
        if (aiOutputs.size() != 5) {
            throw new IllegalStateException("Quiz requires exactly 5 AI outputs");
        }

        Quiz quiz = new Quiz();
        quiz.setUser(user);
        quiz.setFile(file);
        quiz.setTitle(file.getFilename());
        quiz.setCreatedAt(LocalDateTime.now());
        quizRepository.save(quiz);

        IntStream.range(0, aiOutputs.size()).forEach(i -> {
            QuizQuestion question = new QuizQuestion();
            question.setQuiz(quiz);
            question.setAiOutput(aiOutputs.get(i));
            question.setQuestionOrder(i + 1);
            quizQuestionRepository.save(question);
        });

        return quiz;
    }

    @Override
    public boolean quizAlreadyExists(Long fileId, Long userId) {

        return quizRepository.existsByUserIdAndFileId(userId, fileId);
    }
}
