package com.flash.finki.service;

import com.flash.finki.model.QuizQuestion;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionGenerationService {
    @Value("${openai.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<QuizQuestion> generateQuestions(String documentText) {
        try {
            OpenAiService service = new OpenAiService(apiKey, Duration.ofSeconds(30));

            CompletionRequest completionRequest = CompletionRequest.builder()
                    .model("gpt-3.5-turbo-instruct")
                    .prompt(generatePrompt(documentText))
                    .maxTokens(1000)
                    .temperature(0.7)
                    .build();

            CompletionResult result = service.createCompletion(completionRequest);

            String responseText = result.getChoices().get(0).getText().trim();
            log.info("OpenAI Response: {}", responseText);

            return parseQuestionsFromResponse(responseText);
        } catch (Exception e) {
            log.error("Error generating questions", e);
            return new ArrayList<>();
        }
    }

    private String generatePrompt(String text) {
        return "Please generate exactly 5 multiple-choice questions about the following text. " +
        "ALWAYS answer in English and translate the text to English if necessary. " +
        "The correct answer must ALWAYS be the first option (Option A). " +
        "Return ONLY a valid JSON array, and NOTHING else — no explanations, no notes, no markdown formatting like ```json.\n\n" +
        "Provide the response in the EXACT JSON format shown below:\n\n" +
                "[\n" +
                "  {\n" +
                "    \"question\": \"Example question text?\",\n" +
                "    \"options\": [\"Option A\", \"Option B\", \"Option C\", \"Option D\"],\n" +
                "  }\n" +
                "]\n\n" +
                "TEXT TO GENERATE QUESTIONS FROM:\n" + text;
    }
    

    private List<QuizQuestion> parseQuestionsFromResponse(String responseText) {
        try {
            int startIndex = responseText.indexOf('[');
            int endIndex = responseText.lastIndexOf(']') + 1;

            if (startIndex == -1 || endIndex == -1) {
                return convertTextToQuestions(responseText);
            }

            String jsonContent = responseText.substring(startIndex, endIndex);
            log.info("Json content: {}", jsonContent);

            List<QuizQuestion> questions = objectMapper.readValue(
                    jsonContent,
                    new TypeReference<List<QuizQuestion>>(){}
            );

            return questions;
        } catch (Exception e) {
            log.error("Failed to parse JSON, converting text to questions", e);
            return convertTextToQuestions(responseText);
        }
    }

    private List<QuizQuestion> convertTextToQuestions(String text) {
        String[] questionTexts = text.split("\n\n");

        return new ArrayList<>(List.of(questionTexts)).stream()
                .filter(q -> q.contains("?"))
                .map(this::convertSingleQuestionToQuizQuestion)
                .collect(Collectors.toList());
    }

    private QuizQuestion convertSingleQuestionToQuizQuestion(String questionText) {
        QuizQuestion quizQuestion = new QuizQuestion();

        int questionIndex = questionText.indexOf("?");
        quizQuestion.setQuestion(questionText.substring(0, questionIndex + 1).trim());

        // Extract options
        String[] lines = questionText.split("\n");
        List<String> options = new ArrayList<>();
        String correctAnswer = "";

        for (String line : lines) {
            if (line.matches("^[A-D]\\..*")) {
                options.add(line.substring(2).trim());

                // Determine correct answer (simplistic approach)
                if (correctAnswer.isEmpty()) {
                    correctAnswer = line.substring(0, 1);
                }
            }
        }

        quizQuestion.setCorrectAnswer(correctAnswer);
        quizQuestion.setOptions(options);

        return quizQuestion;
    }
}
