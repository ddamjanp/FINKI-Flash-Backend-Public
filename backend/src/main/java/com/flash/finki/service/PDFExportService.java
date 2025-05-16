package com.flash.finki.service;

import com.flash.finki.model.QuizQuestion;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface PDFExportService {

    ByteArrayInputStream exportQuiz(Long quizId);

    ByteArrayInputStream generateQuizPdf(List<QuizQuestion> questions);
}
