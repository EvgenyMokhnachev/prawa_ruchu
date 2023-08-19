package com.mokhnachev.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class BaseGetListFilter<F> {
    private Integer limit;
    private Long offset;
    private Boolean calculateTotal;

    public BaseGetListFilter(Integer limit, Long offset, Boolean calculateTotal) {
        this.limit = limit;
        this.offset = offset;
        this.calculateTotal = calculateTotal;
    }

    public F limit(Integer limit) {
        this.limit = limit;
        return (F) this;
    }

    public F offset(Long offset) {
        this.offset = offset;
        return (F) this;
    }

    public F calculateTotal() {
        this.calculateTotal = true;
        return (F) this;
    }

    public F calculateTotal(Boolean calculateTotal) {
        this.calculateTotal = calculateTotal;
        return (F) this;
    }

}
