package com.flash.finki.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.flash.finki.model.QuizQuestion;
import com.flash.finki.repository.QuizQuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizQuestionService {
    private final QuizQuestionRepository repo;

    public List<QuizQuestion> findFirst5() {
        return repo.findAllBy(PageRequest.of(0, 5));
    }
}
