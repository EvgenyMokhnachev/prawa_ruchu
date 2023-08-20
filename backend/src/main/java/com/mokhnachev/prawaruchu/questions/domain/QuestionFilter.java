package com.mokhnachev.prawaruchu.questions.domain;

import com.mokhnachev.common.BaseGetListFilter;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class QuestionFilter extends BaseGetListFilter<QuestionFilter> {
    private List<Long> ids;
    private Category category;
    private List<KnowType> knowTypes;
    private Boolean bookmarked;
    private List<QuestionType> questionTypes;
    private List<Integer> points;

    public static QuestionFilter create() {
        return new QuestionFilter();
    }

    public QuestionFilter byIds(List<Long> ids) {
        this.ids = ids;
        return this;
    }

    public QuestionFilter byId(Long id) {
        this.ids = List.of(id);
        return this;
    }

    public QuestionFilter byCategory(Category category) {
        this.category = category;
        return this;
    }

    public QuestionFilter byKnowTypes(List<KnowType> knowTypes) {
        this.knowTypes = knowTypes;
        return this;
    }

    public QuestionFilter addKnowType(KnowType knowType) {
        if (this.knowTypes == null) {
            this.knowTypes = new ArrayList<>();
        }
        this.knowTypes.add(knowType);
        return this;
    }

    public QuestionFilter byBookmarked(Boolean bookmarked) {
        this.bookmarked = bookmarked;
        return this;
    }

    public QuestionFilter byQuestionTypes(List<QuestionType> questionTypes) {
        this.questionTypes = questionTypes;
        return this;
    }

    public QuestionFilter byPoints(List<Integer> points) {
        this.points = points;
        return this;
    }
}
