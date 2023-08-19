package com.mokhnachev.prawaruchu.questions.persistance.questions.pgsql;

import com.mokhnachev.common.BasePgSqlRepository;
import com.mokhnachev.common.BaseQuery;
import com.mokhnachev.common.OrderDirection;
import com.mokhnachev.common.Pagination;
import com.mokhnachev.prawaruchu.questions.domain.Category;
import com.mokhnachev.prawaruchu.questions.domain.KnowType;
import com.mokhnachev.prawaruchu.questions.domain.QuestionFilter;
import com.mokhnachev.prawaruchu.questions.domain.QuestionType;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class QuestionsRepositoryByFilterPgSql extends BasePgSqlRepository<QuestionPgSql, QuestionFilter> {

    private static final String FROM = "questions";
    private static final String AS = "quest";

    @Override
    public Pagination<QuestionPgSql> getByFilter(QuestionFilter filter) {
        if (filter.getLimit() == null && filter.getIds() != null && !filter.getIds().isEmpty()) {
            filter.setLimit(filter.getIds().size());
        }

        BaseQuery baseQuery = new BaseQuery(
                FROM, AS,
                String.format("%s.*", AS),
                String.format("%s.id", AS),
                filter.getCalculateTotal(),
                filter.getLimit(),
                filter.getOffset()
        );
        baseQuery.setOptimizedSelectForTotalQuery(String.format("%s.id", AS));

        filterByIds(filter.getIds(), baseQuery);
        filterByCategory(filter.getCategory(), baseQuery);
        filterByKnowTypes(filter.getKnowTypes(), baseQuery);
        filterByBookmarked(filter.getBookmarked(), baseQuery);
        filterByQuestionTypes(filter.getQuestionTypes(), baseQuery);

        injectOrders(filter, baseQuery);

        String sql = buildNativeSqlQuery(baseQuery);
        Query query = entityManager.createNativeQuery(sql, QuestionPgSql.class);
        injectParameters(query, baseQuery.getParameters());
        List<QuestionPgSql> items = query.getResultList();

        return new Pagination<>(items, getTotal(baseQuery));
    }

    private void filterByIds(List<Long> ids, BaseQuery query) {
        if (ids == null || ids.isEmpty()) return;
        query.addParameter("filterByIds", ids);
        query.addWheres(String.format(" AND (%1$s.id IN :filterByIds)", AS));
    }

    private void filterByCategory(Category category, BaseQuery query) {
        if (category == null) return;
        query.addParameter("filterByCategory", category.ordinal());
        query.addWheres(String.format(" AND (:filterByCategory = ANY(%1$s.categories))", AS));
    }

    private void filterByKnowTypes(List<KnowType> knowTypes, BaseQuery query) {
        if (knowTypes == null || knowTypes.isEmpty()) return;
        query.addParameter("filterByKnowTypes", knowTypes.stream().map(Enum::ordinal).collect(Collectors.toList()));
        query.addWheres(String.format(" AND (%1$s.know_type IN (:filterByKnowTypes))", AS));
    }

    private void filterByBookmarked(Boolean bookmarked, BaseQuery query) {
        if (bookmarked == null) return;
        query.addParameter("filterByBookmarked", bookmarked);
        query.addWheres(String.format(" AND (%1$s.bookmarked = :filterByBookmarked)", AS));
    }

    private void filterByQuestionTypes(List<QuestionType> questionTypes, BaseQuery query) {
        if (questionTypes == null || questionTypes.isEmpty()) return;
        query.addParameter("filterByQuestionTypes", questionTypes.stream().map(Enum::ordinal).collect(Collectors.toList()));
        query.addWheres(String.format(" AND (%1$s.question_type IN (:filterByQuestionTypes))", AS));
    }

    private void injectOrders(QuestionFilter filter, BaseQuery query) {
        if (filter == null) return;
        orderById(OrderDirection.DESK, query);
    }

    private void orderById(OrderDirection direction, BaseQuery query) {
        if (direction == null) return;
        query.addOrders(String.format("%1$s.id", AS), direction);
    }

}
