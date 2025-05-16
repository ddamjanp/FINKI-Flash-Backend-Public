package com.flash.finki.service;

import com.flash.finki.model.AIOutput;

import java.util.List;

public interface QuestionGenerationService {

    List<AIOutput> generateQuestions(String documentText);

    String generatePrompt(String text);

    List<AIOutput> parseQuestionsFromResponse(String responseText);
}
