package com.mokhnachev.prawaruchu.questions.persistance.questions.pgsql;

import com.mokhnachev.prawaruchu.questions.domain.Category;
import com.mokhnachev.prawaruchu.questions.domain.KnowType;
import com.mokhnachev.prawaruchu.questions.domain.QuestionType;
import com.mokhnachev.prawaruchu.questions.domain.RightAnswer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "questions")
@NoArgsConstructor
@Data
public class QuestionPgSql {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "name")
    private String name;

    @Column(name = "block_name")
    private String blockName;

    @Column(name = "question_text_pl")
    private String questionTextPL;

    @Column(name = "answer_a_text_pl")
    private String answerATextPL;

    @Column(name = "answer_b_text_pl")
    private String answerBTextPL;

    @Column(name = "answer_c_text_pl")
    private String answerCTextPL;

    @Column(name = "question_text_eng")
    private String questionTextENG;

    @Column(name = "answer_a_text_eng")
    private String answerATextENG;

    @Column(name = "answer_b_text_eng")
    private String answerBTextENG;

    @Column(name = "answer_c_text_eng")
    private String answerCTextENG;

    @Column(name = "question_text_de")
    private String questionTextDE;

    @Column(name = "answer_a_text_de")
    private String answerATextDE;

    @Column(name = "answer_b_text_de")
    private String answerBTextDE;

    @Column(name = "answer_c_text_de")
    private String answerCTextDE;

    @Column(name = "right_answer")
    private RightAnswer rightAnswer;

    @Column(name = "media")
    private String media;

    @Column(name = "question_type")
    private QuestionType questionType;

    @Column(name = "points")
    private int points;

    @Column(name = "categories")
    private List<Category> categories;

    @Column(name = "law")
    private String law;

    @Column(name = "law_description")
    private String lawDescription;

    @Column(name = "security_them_of_question")
    private String securityThemOfQuestion;

    @Column(name = "know_type")
    private KnowType knowType;

    @Column(name = "bookmarked")
    private Boolean bookmarked;

}
