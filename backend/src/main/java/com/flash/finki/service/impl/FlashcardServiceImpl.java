package com.flash.finki.service.impl;

import com.flash.finki.model.AIOutput;
import com.flash.finki.model.File;
import com.flash.finki.model.Flashcard;
import com.flash.finki.model.User;
import com.flash.finki.model.dto.FlashcardDTO;
import com.flash.finki.repository.AIOutputRepository;
import com.flash.finki.repository.FlashcardRepository;
import com.flash.finki.repository.UserRepository;
import com.flash.finki.service.FlashcardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlashcardServiceImpl implements FlashcardService {

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

        File file = aiOutput.getFile();
        if (file == null) {
            throw new RuntimeException("AIOutput is not associated with a file");
        }


        Flashcard flashcard = new Flashcard(
                user,
                aiOutput,
                aiOutput.getQuestion(),
                aiOutput.getCorrectAnswer()
        );
        flashcard.setFile(file);

        return flashcardRepository.save(flashcard);
    }

    public List<FlashcardDTO> searchFlashcards(String query) {
        List<Flashcard> flashcards = flashcardRepository.findByQuestionContainingIgnoreCase(query);
        return flashcards.stream()
                .map(flashcard -> new FlashcardDTO(
                        flashcard.getId(),
                        flashcard.getQuestion(),
                        flashcard.getCorrectAnswer(),
                        flashcard.getCreatedAt()
                ))
                .collect(Collectors.toList());

    }

    public List<FlashcardDTO> getFlashcardsByFile(Long aiOutputId) {

        List<Flashcard> flashcards = flashcardRepository.findByFileId(aiOutputId);

        return flashcards.stream()
                .map(flashcard -> new FlashcardDTO(
                        flashcard.getId(),
                        flashcard.getQuestion(),
                        flashcard.getCorrectAnswer(),
                        flashcard.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    public Flashcard getFlashcardById(Long id) {
        return flashcardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flashcard not found"));
    }
}