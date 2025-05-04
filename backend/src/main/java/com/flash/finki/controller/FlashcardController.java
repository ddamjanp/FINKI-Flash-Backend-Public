package com.flash.finki.controller;

import com.flash.finki.model.Flashcard;
import com.flash.finki.service.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {

    @Autowired
    private FlashcardService flashcardService;

    // Генерирање flashcards од AIOutput
    @PostMapping("/generate/{aiOutputId}")
    public List<Flashcard> generateFlashcards(@PathVariable Long aiOutputId,
                                              @RequestParam Long userId) {
        return flashcardService.generateFromAIOutput(aiOutputId, userId);
    }

    // Пребарување flashcards
    @GetMapping("/search")
    public List<Flashcard> search(@RequestParam String query) {
        return flashcardService.searchFlashcards(query);
    }
}
