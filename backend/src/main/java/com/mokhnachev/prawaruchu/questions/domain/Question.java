package com.mokhnachev.prawaruchu.questions.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Question {
    private Long id;
    private String number;
    private String name;
    private String blockName;

    private String media;
    private QuestionType questionType;
    private int points;
    private List<Category> categories;
    private String law;
    private String lawDescription;
    private String securityThemOfQuestion;

    private String questionTextPL;
    private String answerATextPL;
    private String answerBTextPL;
    private String answerCTextPL;

    private String questionTextENG;
    private String answerATextENG;
    private String answerBTextENG;
    private String answerCTextENG;

    private String questionTextDE;
    private String answerATextDE;
    private String answerBTextDE;
    private String answerCTextDE;

    private RightAnswer rightAnswer;

    private KnowType knowType;
    private Boolean bookmarked;
}
