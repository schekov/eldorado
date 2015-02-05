package com.getjavajob.schekovskiy.list2.task1.model;

import java.math.BigDecimal;

public class Position {

    public Long id;
    public BigDecimal price;
    public Long count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Position{ " +
                "\n\t\t\t\tid='" + id + '\'' +
                ", \n\t\t\t\tprice='" + getPrice() + '\'' +
                ", \n\t\t\t\tcount='" + count + '\'' +
                "}";
    }
}
