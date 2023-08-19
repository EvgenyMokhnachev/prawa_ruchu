package com.mokhnachev.prawaruchu.questions.domain;

import com.mokhnachev.common.Pagination;

public interface QuestionRepository {
    Question save(Question data);
    Pagination<Question> get(QuestionFilter filter);
}
