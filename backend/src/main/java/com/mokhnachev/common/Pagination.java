package com.mokhnachev.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagination<T> implements Serializable {
    private List<T> list;
    private Long total;

    public T getFirst() {
        if (this.list == null || this.list.isEmpty()) return null;
        return this.list.get(0);
    }

    public <V> Pagination<V> map(Function<T, V> mapper) {
        return new Pagination<>(list.stream().map(mapper).collect(Collectors.toCollection(()->new ArrayList<>(list.size()))), total);
    }

    public boolean isEmpty() {
        return this.list == null || this.list.isEmpty();
    }
}
