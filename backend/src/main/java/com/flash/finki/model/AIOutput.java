package com.flash.finki.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ai_output")
public class AIOutput {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void setOptions(List<String> options) {
        if (options == null || options.size() != 4) {
            throw new IllegalArgumentException("Options list must contain exactly 4 elements.");
        }
        this.correctAnswer = options.get(0);
        this.wrongAnswer1 = options.get(1);
        this.wrongAnswer2 = options.get(2);
        this.wrongAnswer3 = options.get(3);
    }
}
