package com.flash.finki.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.flash.finki.model.QuizQuestion;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@Service
public class PDFExportService {

    public ByteArrayInputStream exportFlashcards(List<QuizQuestion> questions) {
        return generatePdf(questions, true);
    }

    public ByteArrayInputStream exportQuizzes(List<QuizQuestion> questions) {
        return generatePdf(questions, false);
    }

    private ByteArrayInputStream generatePdf(List<QuizQuestion> questions, boolean flashcardStyle) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            try (com.itextpdf.kernel.pdf.PdfDocument pdfDoc = 
                         new com.itextpdf.kernel.pdf.PdfDocument(writer);
                 Document doc = new Document(pdfDoc)) {

                var font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
                doc.setFont(font);

                int idx = 1;
                for (QuizQuestion q : questions) {
                    if (flashcardStyle) {

                        doc.add(new Paragraph("Card " + (idx++)).setBold());
                        doc.add(new Paragraph(q.getAiOutput().getQuestion()));
                        doc.add(new Paragraph("— flip for answer —").setItalic());
                        doc.add(new Paragraph("\n"));

                        doc.add(new Paragraph("Answer").setUnderline());
                        doc.add(new Paragraph(q.getAiOutput().getCorrectAnswer()));
                        doc.add(new com.itextpdf.layout.element.AreaBreak());
                    } else {
                        doc.add(new Paragraph(idx + ". " + q.getAiOutput().getQuestion()));
                        doc.add(new Paragraph("\n\n\n"));  
                        idx++;
                    }
                }
            }

            return new ByteArrayInputStream(baos.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Failed to export PDF", e);
        }
    }
}
