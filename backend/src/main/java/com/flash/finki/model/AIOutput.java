package com.flash.finki.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ai_output")
public class AIOutput {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @Column(nullable = false)
    private String question;

    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer;

    @Column(name = "wrong_answer_1", nullable = false)
    private String wrongAnswer1;

    @Column(name = "wrong_answer_2", nullable = false)
    private String wrongAnswer2;

    @Column(name = "wrong_answer_3", nullable = false)
    private String wrongAnswer3;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}