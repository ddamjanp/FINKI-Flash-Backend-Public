package com.flash.finki.controller;

import com.flash.finki.model.Flashcard;
import com.flash.finki.service.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {

    private final FlashcardService flashcardService;

    @Autowired
    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @PostMapping("/generate/{aiOutputId}")
    public ResponseEntity<Flashcard> generateFlashcard(
            @PathVariable Long aiOutputId,
            @RequestParam Long userId) {
        Flashcard flashcard = flashcardService.generateFromAIOutput(aiOutputId, userId);
        return ResponseEntity.ok(flashcard);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Flashcard>> search(@RequestParam String query) {
        List<Flashcard> flashcards = flashcardService.searchFlashcards(query);
        return ResponseEntity.ok(flashcards);
    }

    @GetMapping("/file/{fileId}")
    public ResponseEntity<List<Flashcard>> getFlashcardsByFile(@PathVariable Long fileId) {
        List<Flashcard> flashcards = flashcardService.getFlashcardsByFile(fileId);
        return ResponseEntity.ok(flashcards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flashcard> getFlashcard(@PathVariable Long id) {
        Flashcard flashcard = flashcardService.getFlashcardById(id);
        return ResponseEntity.ok(flashcard);
    }
}
