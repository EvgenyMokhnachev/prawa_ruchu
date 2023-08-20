package com.mokhnachev.prawaruchu.questions.api;

import com.mokhnachev.prawaruchu.questions.domain.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetQuestionsRequestFilter {
    private Boolean badKnow;
    private Boolean bookmarked;
    private Boolean dontKnow;
    private Boolean goodKnow;
    private Integer limit;
    private Long offset;
    private QuestionType[] questionTypes;
    private Integer[] points;
}
