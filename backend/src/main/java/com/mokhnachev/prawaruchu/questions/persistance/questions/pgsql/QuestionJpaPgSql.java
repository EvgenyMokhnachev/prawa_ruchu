package com.mokhnachev.prawaruchu.questions.persistance.questions.pgsql;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionJpaPgSql extends CrudRepository<QuestionPgSql, Long> {

}
