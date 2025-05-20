package com.flash.finki.model.dto;

import com.flash.finki.model.AIOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartQuizAttemptResponseDTO {
    private Long attemptId;
    private List<AIOutput> aiOutputs;
    private List<Long> quizQuestionIds;
}
