package com.flash.finki.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quiz_attempt_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizAttemptAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "attempt_id")
    private QuizAttempt attempt;

    @ManyToOne(optional = false)
    @JoinColumn(name="quiz_question_id", nullable = false)
    private QuizQuestion quizQuestion;

    @Column(name = "selected_answer", nullable = false)
    private String selectedAnswer;

    @Column(name="is_correct", nullable = false)
    private boolean isCorrect;
}
