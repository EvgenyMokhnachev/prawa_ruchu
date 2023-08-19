package com.mokhnachev.prawaruchu.questions.api;

import com.mokhnachev.common.Pagination;
import com.mokhnachev.prawaruchu.questions.domain.Question;
import com.mokhnachev.prawaruchu.questions.domain.QuestionFilter;
import com.mokhnachev.prawaruchu.questions.domain.QuestionRepository;
import com.mokhnachev.prawaruchu.questions.persistance.questions.xml.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class QuestionsAdminController {

    @Autowired
    private QuestionRepository questionRepository;

    @Value("${application.questions_xml_path}")
    private String questionsXmlPath;

    @GetMapping("/parse")
    public String index() {
        Pagination<Question> actualDatabase = questionRepository.get(QuestionFilter.create().limit(1).calculateTotal());
        if (!actualDatabase.isEmpty()) {
            return "ALREADY PARSED: " + actualDatabase.getTotal();
        }

        ExcelReader reader = new ExcelReader();
        List<Question> questions = reader.parseQuestionsXML(questionsXmlPath);
        questions.forEach(questionRepository::save);
        return "OK";
    }

}
