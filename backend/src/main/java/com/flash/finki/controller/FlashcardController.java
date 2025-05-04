package com.flash.finki.controller;

import com.flash.finki.model.Flashcard;
import com.flash.finki.service.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// FlashcardController.java
@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {

    @Autowired
    private FlashcardService flashcardService;

    @PostMapping("/generate/{aiOutputId}")
    public Flashcard generateFlashcard( // Changed to singular return type
                                        @PathVariable Long aiOutputId, // Changed to UUID
                                        @RequestParam Long userId      // Changed to UUID
    ) {
        return flashcardService.generateFromAIOutput(aiOutputId, userId);
    }

    @GetMapping("/search")
    public List<Flashcard> search(@RequestParam String query) {
        return flashcardService.searchFlashcards(query);
    }
}
