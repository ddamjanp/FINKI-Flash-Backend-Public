package com.flash.finki.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz_questions")
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "ai_output_id", nullable = false)
    private AIOutput aiOutput;

    @Column(nullable = false)
    @JoinColumn(name = "question", nullable = false)
    private String question;

    @Column(nullable = false)
    @JoinColumn(name = "correct_answer", nullable = false)
    private String correctAnswer;
    
    @Column(nullable = false)
    @JoinColumn(name = "wrong_answer_1", nullable = false)
    private String wrongAnswer1;    

    @Column(nullable = false)
    @JoinColumn(name = "wrong_answer_2", nullable = false)
    private String wrongAnswer2;    
    
    @Column(nullable = false)
    @JoinColumn(name = "wrong_answer_3", nullable = false)
    private String wrongAnswer3;

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