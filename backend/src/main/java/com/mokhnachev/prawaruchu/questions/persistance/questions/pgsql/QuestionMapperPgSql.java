package com.mokhnachev.prawaruchu.questions.persistance.questions.pgsql;

import com.mokhnachev.prawaruchu.questions.domain.Category;
import com.mokhnachev.prawaruchu.questions.domain.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapperPgSql {

    public QuestionPgSql map(Question data) {
        QuestionPgSql item = new QuestionPgSql();
        item.setId(data.getId());
        item.setNumber(data.getNumber());
        item.setName(data.getName());
        item.setBlockName(data.getBlockName());

        item.setMedia(data.getMedia());
        item.setQuestionType(data.getQuestionType());
        item.setPoints(data.getPoints());
        item.setCategories(data.getCategories());
        item.setLaw(data.getLaw());
        item.setLawDescription(data.getLawDescription());
        item.setSecurityThemOfQuestion(data.getSecurityThemOfQuestion());

        item.setQuestionTextPL(data.getQuestionTextPL());
        item.setAnswerATextPL(data.getAnswerATextPL());
        item.setAnswerBTextPL(data.getAnswerBTextPL());
        item.setAnswerCTextPL(data.getAnswerCTextPL());

        item.setQuestionTextENG(data.getQuestionTextENG());
        item.setAnswerATextENG(data.getAnswerATextENG());
        item.setAnswerBTextENG(data.getAnswerBTextENG());
        item.setAnswerCTextENG(data.getAnswerCTextENG());

        item.setQuestionTextDE(data.getQuestionTextDE());
        item.setAnswerATextDE(data.getAnswerATextDE());
        item.setAnswerBTextDE(data.getAnswerBTextDE());
        item.setAnswerCTextDE(data.getAnswerCTextDE());

        item.setRightAnswer(data.getRightAnswer());
        item.setKnowType(data.getKnowType());
        item.setBookmarked(data.getBookmarked());

        return item;
    }

    public Question map(QuestionPgSql data) {
        Question item = new Question();
        item.setId(data.getId());
        item.setNumber(data.getNumber());
        item.setName(data.getName());
        item.setBlockName(data.getBlockName());

        item.setMedia(data.getMedia());
        item.setQuestionType(data.getQuestionType());
        item.setPoints(data.getPoints());
        item.setCategories(data.getCategories());
        item.setLaw(data.getLaw());
        item.setLawDescription(data.getLawDescription());
        item.setSecurityThemOfQuestion(data.getSecurityThemOfQuestion());

        item.setQuestionTextPL(data.getQuestionTextPL());
        item.setAnswerATextPL(data.getAnswerATextPL());
        item.setAnswerBTextPL(data.getAnswerBTextPL());
        item.setAnswerCTextPL(data.getAnswerCTextPL());

        item.setQuestionTextENG(data.getQuestionTextENG());
        item.setAnswerATextENG(data.getAnswerATextENG());
        item.setAnswerBTextENG(data.getAnswerBTextENG());
        item.setAnswerCTextENG(data.getAnswerCTextENG());

        item.setQuestionTextDE(data.getQuestionTextDE());
        item.setAnswerATextDE(data.getAnswerATextDE());
        item.setAnswerBTextDE(data.getAnswerBTextDE());
        item.setAnswerCTextDE(data.getAnswerCTextDE());

        item.setRightAnswer(data.getRightAnswer());
        item.setKnowType(data.getKnowType());
        item.setBookmarked(data.getBookmarked());

        return item;
    }

}
