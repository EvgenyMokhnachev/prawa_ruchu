package com.mokhnachev.common;

import lombok.Data;

@Data
public class OrderDirectionConfig {
    private OrderDirection direction;
    private OrderNullsDirection nullsDirection;

    public OrderDirectionConfig(OrderDirection direction, OrderNullsDirection nullsDirection) {
        this.direction = direction;
        this.nullsDirection = nullsDirection;
    }

    public OrderDirectionConfig(OrderDirection direction) {
        this.direction = direction;
        this.nullsDirection = null;
    }
}
