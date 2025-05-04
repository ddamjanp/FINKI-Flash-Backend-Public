package com.flash.finki.service;

import com.flash.finki.model.AIOutput;
import com.flash.finki.model.Flashcard;
import com.flash.finki.model.User;
import com.flash.finki.repository.AIOutputRepository;
import com.flash.finki.repository.FlashcardRepository;
import com.flash.finki.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class FlashcardService {

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private AIOutputRepository aiOutputRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Flashcard> generateFromAIOutput(Long aiOutputId, Long userId) {
        AIOutput aiOutput = aiOutputRepository.findById(aiOutputId)
                .orElseThrow(() -> new RuntimeException("AIOutput not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        Flashcard flashcard = new Flashcard(
                user,
                aiOutput,
                aiOutput.getQuestion(),
                aiOutput.getCorrectAnswer()
        );

        return List.of(flashcardRepository.save(flashcard));
    }

    public List<Flashcard> searchFlashcards(String query) {
        return flashcardRepository.findByQuestionContainingIgnoreCase(query);
    }
}

