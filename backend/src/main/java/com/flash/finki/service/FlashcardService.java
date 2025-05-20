package com.flash.finki.service;

import com.flash.finki.model.Flashcard;
import com.flash.finki.model.dto.FlashcardDTO;

import java.util.List;

public interface FlashcardService {

    Flashcard generateFromAIOutput(Long aiOutputId, Long userId);

    List<FlashcardDTO> searchFlashcards(String query);

    List<FlashcardDTO> getFlashcardsByFile(Long aiOutputId);

    Flashcard getFlashcardById(Long id);
}
