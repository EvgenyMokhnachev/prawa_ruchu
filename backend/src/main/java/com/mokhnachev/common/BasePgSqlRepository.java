package com.mokhnachev.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.*;

@Repository
public abstract class BasePgSqlRepository<I, F> {

    @Autowired
    @PersistenceContext
    protected EntityManager entityManager;

    public abstract Pagination<I> getByFilter(F filter);

    protected String listIdsToStringWithComma(List<Long> list) {
        StringBuilder idsBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            idsBuilder.append(list.get(i));
            if (i < list.size() - 1) idsBuilder.append(",");
        }
        return idsBuilder.toString();
    }

    protected String stringsIdsToStringWithComma(List<String> list) {
        List<String> items = new ArrayList<>();
        for (String item : list) {
            items.add(String.format("'%s'", item));
        }
        return listStringsToStringWithDelimiter(items, ",");
    }

    protected String listStringsToStringWithDelimiter(List<String> list, String delimiter) {
        StringBuilder idsBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            idsBuilder.append(list.get(i));
            if (i < list.size() - 1) idsBuilder.append(delimiter);
        }
        return idsBuilder.toString();
    }

    protected Long getTotalQuery(String sqlQuery) {
        return getTotalQuery(sqlQuery, null);
    }

    protected Long getTotalQuery(String sqlQuery, Map<String, Object> parameters) {
        String sql = "SELECT COUNT(*) FROM (" + sqlQuery + ") AS total";
        Query totalQuery = entityManager.createNativeQuery(sql);
        totalQuery = injectParameters(totalQuery, parameters);
        Object singleResult = totalQuery.getSingleResult();
        return singleResult.getClass().equals(Long.class) ? (Long) singleResult : ((BigInteger) singleResult).longValue();
    }

    protected StringBuilder buildPagination(Integer limit, Long offset) {
        StringBuilder sql = new StringBuilder();
        if (limit != null) sql.append(String.format(" LIMIT %d", limit));
        if (offset != null) sql.append(String.format(" OFFSET %d", offset));
        return sql;
    }

    protected Query injectParameters(Query query, Map<String, Object> parameters) {
        if (parameters != null) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                query = query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query;
    }

    protected String buildNativeSqlQuery(BaseQuery query) {
        StringBuilder sql = new StringBuilder()
                .append(this.buildSelects(query))
                .append(" ")
                .append(this.buildFrom(query))
                .append(" ")
                .append(this.buildJoins(query))
                .append(" ")
                .append(this.buildWhere(query))
                .append(" ")
                .append(this.buildGroups(query))
                .append(" ")
                .append(this.buildHaving(query))
                .append(" ")
                .append(this.buildOrders(query))
                .append(" ")
                .append(this.buildPagination(query.getLimit(), query.getOffset()))
                .append(" ");

        return sql.toString();
    }

    protected Long getTotal(BaseQuery query) {
        if (query.getCalculateTotal() != null && !query.getCalculateTotal()) {
            return null;
        }

        StringBuilder sql = new StringBuilder()
                .append(this.buildSelectsForPagination(query))
                .append(" ")
                .append(this.buildFrom(query))
                .append(" ")
                .append(this.buildJoins(query))
                .append(" ")
                .append(this.buildWhere(query))
                .append(" ")
                .append(this.buildGroups(query))
                .append(" ")
                .append(this.buildHaving(query))
                .append(" ");

        return getTotalQuery(sql.toString(), query.getParameters());
    }

    private StringBuilder buildSelects(BaseQuery query) {
        StringBuilder result = new StringBuilder();
        result.append("SELECT ");
        return result.append(String.join(", ", query.getSelects()));
    }

    private String buildFrom(BaseQuery query) {
        return String.format(" FROM %s AS %s", query.getFrom(), query.getAs());
    }

    private StringBuilder buildSelectsForPagination(BaseQuery query) {
        return (query.getSelectForTotalQuery() != null)
                ? new StringBuilder("SELECT " + query.getSelectForTotalQuery())
                : buildSelects(query);
    }

    private StringBuilder buildJoins(BaseQuery query) {
        return new StringBuilder(
                query.getJoins() == null || query.getJoins().isEmpty()
                        ? ""
                        : String.join(" ", query.getJoins())
        );
    }

    private StringBuilder buildWhere(BaseQuery query) {
        StringBuilder sql = new StringBuilder();

        if (query.getWheres() == null || query.getWheres().isEmpty()) {
            return sql;
        }

        sql.append(" WHERE (1 = 1) ");
        sql.append(String.join(" ", query.getWheres()));

        return sql;
    }

    private StringBuilder buildGroups(BaseQuery query) {
        StringBuilder sql = new StringBuilder();

        if (query.getGroups() == null || query.getGroups().isEmpty()) {
            return sql;
        }

        sql.append(" GROUP BY ");
        sql.append(String.join(", ", query.getGroups()));

        return sql;
    }

    private StringBuilder buildHaving(BaseQuery query) {
        StringBuilder sql = new StringBuilder();

        if (query.getHaving() == null || query.getHaving().isEmpty()) {
            return sql;
        }

        sql.append(" HAVING (1 = 1) ");
        sql.append(String.join(", ", query.getHaving()));

        return sql;
    }

    private StringBuilder buildOrders(BaseQuery query) {
        StringBuilder sql = new StringBuilder();

        if (query.getOrders() == null || query.getOrders().isEmpty()) {
            return sql;
        }

        Set<String> ordersConcatenated = new LinkedHashSet<>();
        for (Map.Entry<String, OrderDirectionConfig> entry : query.getOrders().entrySet()) {
            OrderDirectionConfig directionConfig = entry.getValue();
            if (directionConfig == null) continue;

            OrderDirection orderDirection = directionConfig.getDirection();

            ordersConcatenated.add(String.format(
                    "%s %s %s",
                    entry.getKey(),
                    (
                            (orderDirection == null || orderDirection.equals(OrderDirection.DESK))
                                    ? "DESC"
                                    : "ASC"
                    ),
                    (
                            (directionConfig.getNullsDirection() == null)
                                    ? ""
                                    : (directionConfig.getNullsDirection().equals(OrderNullsDirection.FIRST))
                                    ? "NULLS FIRST"
                                    : "NULLS LAST"
                    )
            ));
        }

        sql.append(" ORDER BY ");
        sql.append(String.join(", ", ordersConcatenated));

        return sql;
    }

}
