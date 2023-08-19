package com.mokhnachev.common;

import com.google.gson.internal.LinkedTreeMap;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class BaseQuery {
    private Long parameterKeySequence = 1L;
    private String from;
    private String as;
    private List<String> selects = new ArrayList<>();
    private List<String> joins = new ArrayList<>();
    private List<String> wheres = new ArrayList<>();
    private List<String> groups = new ArrayList<>();
    private List<String> having = new ArrayList<>();
    private Map<String, Object> parameters = new HashMap<>();
    private Map<String, OrderDirectionConfig> orders = new LinkedTreeMap<>();
    private Boolean calculateTotal = null;
    private String selectForTotalQuery = null;
    private Integer limit = null;
    private Long offset = null;


    public BaseQuery(String from, String as, String select, String groupBy, BaseGetListFilter filter) {
        this(from, as, select, groupBy, filter.getCalculateTotal(), filter.getLimit(), filter.getOffset());
    }

    public BaseQuery(String from, String as, String select, String groupBy, Boolean calculateTotal, Integer limit, Long offset) {
        this.from = from;
        this.as = as;
        this.addSelects(select);
        this.calculateTotal = calculateTotal;
        this.limit = limit;
        this.offset = offset;
        addGroups(groupBy);
    }

    public void addSelects(String select) {
        if (select != null && !this.selects.contains(select)) {
            this.selects.add(select);
        }
    }

    public void addJoins(String join) {
        if (join != null && !this.joins.contains(join)) {
            this.joins.add(join);
        }
    }

    public void addWheres(String where) {
        if (where != null && !this.wheres.contains(where)) {
            this.wheres.add(where);
        }
    }

    public void addGroups(String group) {
        if (group != null && !this.groups.contains(group)) {
            this.groups.add(group);
        }
    }

    public void addHaving(String having) {
        if (having != null && !this.having.contains(having)) {
            this.having.add(having);
        }
    }

    public void addOrders(String field, OrderDirection direction) {
        this.orders.put(field, new OrderDirectionConfig(direction, null));
    }

    public void addOrders(String field, OrderDirection direction, OrderNullsDirection nullsDirection) {
        this.orders.put(field, new OrderDirectionConfig(direction, nullsDirection));
    }

    public void setOptimizedSelectForTotalQuery(String selectForTotalQuery) {
        this.selectForTotalQuery = selectForTotalQuery;
    }

    public void addParameter(String parameter, Object value) {
        this.parameters.put(parameter, value);
    }

    public String addParameter(Object value) {
        String parameterKey = generateParameterKey();
        this.parameters.put(parameterKey, value);
        return parameterKey;
    }

    public String generateParameterKey() {
        return ("parameterNo" + parameterKeySequence++);
    }

}
