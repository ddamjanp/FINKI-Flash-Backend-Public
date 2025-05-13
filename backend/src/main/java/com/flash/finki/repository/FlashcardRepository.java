package com.flash.finki.repository;

import com.flash.finki.model.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    List<Flashcard> findByQuestionContainingIgnoreCase(String query);

    List<Flashcard> findByFileId(Long fileId);
}
