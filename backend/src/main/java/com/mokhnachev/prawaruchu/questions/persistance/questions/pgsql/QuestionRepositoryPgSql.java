package com.mokhnachev.prawaruchu.questions.persistance.questions.pgsql;

import com.mokhnachev.common.Pagination;
import com.mokhnachev.prawaruchu.questions.domain.KnowType;
import com.mokhnachev.prawaruchu.questions.domain.Question;
import com.mokhnachev.prawaruchu.questions.domain.QuestionFilter;
import com.mokhnachev.prawaruchu.questions.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionRepositoryPgSql implements QuestionRepository {

    @Autowired
    private QuestionMapperPgSql mapperPgSql;

    @Autowired
    private QuestionJpaPgSql jpaPgSql;

    @Autowired
    private QuestionsRepositoryByFilterPgSql byFilterPgSql;

    @Override
    public Question save(Question data) {
        if (data.getKnowType() == null) {
            data.setKnowType(KnowType.MAYBE);
        }
        if (data.getBookmarked() == null) {
            data.setBookmarked(Boolean.FALSE);
        }
        return mapperPgSql.map(jpaPgSql.save(mapperPgSql.map(data)));
    }

    @Override
    public Pagination<Question> get(QuestionFilter filter) {
        Pagination<QuestionPgSql> byFilter = byFilterPgSql.getByFilter(filter);
        return byFilter.map(mapperPgSql::map);
    }
}
