package com.flash.finki.service;

import com.flash.finki.model.AIOutput;
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

    public List<AIOutput> generateQuestions(String documentText) {
        try {
            OpenAiService service = new OpenAiService(apiKey, Duration.ofSeconds(30));

            CompletionRequest completionRequest = CompletionRequest.builder()
                    .model("gpt-3.5-turbo-instruct")
                    .prompt(generatePrompt(documentText))
                    .maxTokens(2000)
                    .temperature(0.3) // Lower temperature for more consistent output
                    .topP(1.0)
                    .frequencyPenalty(0.0)
                    .presencePenalty(0.0)
                    .stop(null) // No stop sequence
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
        return "You are a quiz generator. Generate exactly 5 multiple-choice questions about the following text. " +
                "Follow these STRICT requirements:\n" +
                "1. Return ONLY a valid JSON array\n" +
                "2. Each question MUST have exactly 4 options\n" +
                "3. The first option (index 0) MUST be the correct answer\n" +
                "4. Do not include 'Option A:', 'Option B:', etc. in the options\n" +
                "5. Do not add any explanation or notes\n" +
                "6. Ensure the JSON is properly formatted with no trailing commas\n\n" +
                "Required JSON format:\n" +
                "[\n" +
                "  {\n" +
                "    \"question\": \"What is the example question?\",\n" +
                "    \"options\": [\"Correct answer\", \"Wrong answer 1\", \"Wrong answer 2\", \"Wrong answer 3\"]\n" +
                "  }\n" +
                "]\n\n" +
                "Text to generate questions from:\n\n" + text + "\n\n" +
                "Remember: Return ONLY the JSON array with exactly 5 questions, each with exactly 4 options.";
    }

    private List<AIOutput> parseQuestionsFromResponse(String responseText) {
        try {
            // Clean up the response text to ensure valid JSON
            String cleanedResponse = responseText.trim()
                    .replaceAll("```json\\s*", "") // Remove any markdown code block indicators
                    .replaceAll("```\\s*", "") // Remove ending markdown indicators
                    .replaceAll("Option [A-D]:\\s*", "") // Remove option prefixes
                    .replaceAll(",\\s*}", "}") // Remove trailing commas before closing braces
                    .replaceAll(",\\s*]", "]"); // Remove trailing commas before closing brackets

            int startIndex = cleanedResponse.indexOf('[');
            int endIndex = cleanedResponse.lastIndexOf(']') + 1;

            if (startIndex == -1 || endIndex == -1) {
                log.error("Invalid JSON format - missing brackets. Response: {}", cleanedResponse);
                return new ArrayList<>();
            }

            String jsonContent = cleanedResponse.substring(startIndex, endIndex);
            log.info("Cleaned Json content: {}", jsonContent);

            List<AIOutput> aiOutputs = objectMapper.readValue(
                    jsonContent,
                    new TypeReference<List<AIOutput>>() {
                    });

            // Validate questions
            if (aiOutputs.size() != 5) {
                log.error("Invalid number of questions: {}", aiOutputs.size());
                return new ArrayList<>();
            }

            return aiOutputs;
        } catch (Exception e) {
            log.error("Failed to parse JSON: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<AIOutput> convertTextToQuestions(String text) {
        String[] questionTexts = text.split("\n\n");

        return new ArrayList<>(List.of(questionTexts)).stream()
                .filter(q -> q.contains("?"))
                .map(this::convertSingleQuestionToAIOutput)
                .collect(Collectors.toList());
    }

    private AIOutput convertSingleQuestionToAIOutput(String questionText) {
        AIOutput aiOutput = new AIOutput();

        int questionIndex = questionText.indexOf("?");
        aiOutput.setQuestion(questionText.substring(0, questionIndex + 1).trim());

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

        aiOutput.setCorrectAnswer(correctAnswer);
        aiOutput.setOptions(options);

        return aiOutput;
    }
}
