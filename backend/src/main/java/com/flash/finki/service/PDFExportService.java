package com.flash.finki.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.flash.finki.model.QuizQuestion;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PDFExportService {

    private final QuizQuestionService quizQuestionService;

    public ByteArrayInputStream exportQuiz(Long quizId) {
        List<QuizQuestion> questions = quizQuestionService.findByQuizId(quizId);
        return generateQuizPdf(questions);
    }

    private ByteArrayInputStream generateQuizPdf(List<QuizQuestion> questions) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            try (com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
                    Document doc = new Document(pdfDoc)) {

                PdfFont regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
                PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
                doc.setFont(regularFont);

                // Add title
                Paragraph title = new Paragraph("QUIZ")
                        .setFont(boldFont)
                        .setFontSize(24)
                        .setTextAlignment(TextAlignment.CENTER);
                doc.add(title);
                doc.add(new Paragraph("\n\n"));

                // Add questions
                int questionNumber = 1;
                for (QuizQuestion q : questions) {
                    // Question
                    doc.add(new Paragraph(questionNumber + ". " + q.getAiOutput().getQuestion())
                            .setFont(boldFont)
                            .setFontSize(14));
                    doc.add(new Paragraph("\n"));

                    // Create a list of all answers, keeping track of which one is correct
                    List<String> allAnswers = new ArrayList<>(Arrays.asList(
                            q.getAiOutput().getCorrectAnswer(),
                            q.getAiOutput().getWrongAnswer1(),
                            q.getAiOutput().getWrongAnswer2(),
                            q.getAiOutput().getWrongAnswer3()));

                    String correctAnswer = q.getAiOutput().getCorrectAnswer();

                    // Add each answer with the appropriate formatting
                    char answerLetter = 'a';
                    for (String answer : allAnswers) {
                        String prefix = answerLetter + ") ";
                        Paragraph answerParagraph = new Paragraph(prefix + answer);

                        // If this is the correct answer, make it bold
                        if (answer.equals(correctAnswer)) {
                            answerParagraph.setFont(boldFont);
                        } else {
                            answerParagraph.setFont(regularFont);
                        }

                        doc.add(answerParagraph);
                        answerLetter++;
                    }

                    doc.add(new Paragraph("\n\n"));
                    questionNumber++;
                }
            }

            return new ByteArrayInputStream(baos.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Failed to export PDF", e);
        }
    }
}
