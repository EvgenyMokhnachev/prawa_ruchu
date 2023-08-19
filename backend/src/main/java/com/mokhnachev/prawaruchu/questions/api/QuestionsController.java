package com.mokhnachev.prawaruchu.questions.api;

import com.mokhnachev.common.Pagination;
import com.mokhnachev.prawaruchu.questions.domain.*;
import com.mokhnachev.prawaruchu.questions.persistance.questions.xml.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController()
public class QuestionsController {

    @Autowired
    private QuestionRepository questionRepository;

    @Value("${application.media_database.directory_path}")
    private String mediaDatabaseDirectoryPath;

    @PostMapping("/getQuestions")
    public Pagination<Question> getQuestions(@RequestBody GetQuestionsRequestFilter getQuestionsRequestFilter) {
        QuestionFilter filter = QuestionFilter.create();

        if (getQuestionsRequestFilter.getLimit() != null) {
            filter.limit(getQuestionsRequestFilter.getLimit());
        } else {
            filter.limit(5);
        }

        if (getQuestionsRequestFilter.getOffset() != null) {
            filter.offset(getQuestionsRequestFilter.getOffset());
        }

        if (getQuestionsRequestFilter.getBookmarked() != null && getQuestionsRequestFilter.getBookmarked()) {
            filter.byBookmarked(true);
        }

        if (getQuestionsRequestFilter.getGoodKnow() != null && getQuestionsRequestFilter.getGoodKnow()) {
            filter.addKnowType(KnowType.GOOD_KNOW);
        }

        if (getQuestionsRequestFilter.getBadKnow() != null && getQuestionsRequestFilter.getBadKnow()) {
            filter.addKnowType(KnowType.MAYBE);
        }

        if (getQuestionsRequestFilter.getDontKnow() != null && getQuestionsRequestFilter.getDontKnow()) {
            filter.addKnowType(KnowType.DOT_KNOW);
        }

        if (getQuestionsRequestFilter.getQuestionTypes() != null && getQuestionsRequestFilter.getQuestionTypes().length > 0) {
            filter.byQuestionTypes(List.of(getQuestionsRequestFilter.getQuestionTypes()));
        }

        filter.byCategory(Category.A);

        return questionRepository.get(filter);
    }

    @RequestMapping(path = "/getMedia/{mediaQuery}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getMedia(@PathVariable("mediaQuery") String mediaQuery) throws IOException {

        File file = new File(String.format("%s/%s", mediaDatabaseDirectoryPath, mediaQuery));

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @RequestMapping(path = "/setKnowType/{questionId}/{knowType}", method = RequestMethod.POST)
    public Question setKnowType(
            @PathVariable("questionId") Long questionId,
            @PathVariable("knowType") String knowType
    ) {
        Question question = questionRepository.get(QuestionFilter.create().limit(1).byId(questionId)).getFirst();
        if (question == null) return null;

        KnowType parsedKnowType = switch (knowType) {
            case "GOOD_KNOW" -> KnowType.GOOD_KNOW;
            case "MAYBE" -> KnowType.MAYBE;
            case "DONT_KNOW" -> KnowType.DOT_KNOW;
            default -> null;
        };
        if (parsedKnowType == null) return null;

        question.setKnowType(parsedKnowType);
        questionRepository.save(question);

        return question;
    }

    @RequestMapping(path = "/setBookmarked/{questionId}/{bookmarked}", method = RequestMethod.POST)
    public Question setBookmarked(
            @PathVariable("questionId") Long questionId,
            @PathVariable("bookmarked") String bookmarked
    ) {
        Question question = questionRepository.get(QuestionFilter.create().limit(1).byId(questionId)).getFirst();
        if (question == null) return null;

        question.setBookmarked(bookmarked.toLowerCase().trim().equals("true"));
        questionRepository.save(question);

        return question;
    }

}
