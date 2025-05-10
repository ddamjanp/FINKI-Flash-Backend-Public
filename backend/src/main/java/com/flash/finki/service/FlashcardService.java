package com.flash.finki.service;

import com.flash.finki.model.AIOutput;
import com.flash.finki.model.Flashcard;
import com.flash.finki.model.User;
import com.flash.finki.repository.AIOutputRepository;
import com.flash.finki.repository.FlashcardRepository;
import com.flash.finki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;
    private final AIOutputRepository aiOutputRepository;
    private final UserRepository userRepository;

    public Flashcard generateFromAIOutput(Long aiOutputId, Long userId) {
        AIOutput aiOutput = aiOutputRepository.findById(aiOutputId)
                .orElseThrow(() -> new RuntimeException("AIOutput not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (aiOutput.getQuestion() == null || aiOutput.getCorrectAnswer() == null) {
            throw new RuntimeException("AIOutput is missing question or answer");
        }

        Flashcard flashcard = new Flashcard(
                user,
                aiOutput,
                aiOutput.getQuestion(),
                aiOutput.getCorrectAnswer()
        );

        return flashcardRepository.save(flashcard);
    }

    public List<Flashcard> searchFlashcards(String query) {
        return flashcardRepository.findByQuestionContainingIgnoreCase(query);
    }

    public List<Flashcard> getFlashcardsByFile(Long aiOutputId) {
        return flashcardRepository.findByAiOutputId(aiOutputId);
    }

    public Flashcard getFlashcardById(Long id) {
        return flashcardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flashcard not found"));
    }
}