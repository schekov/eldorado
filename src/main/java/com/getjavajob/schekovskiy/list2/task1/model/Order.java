package com.getjavajob.schekovskiy.list2.task1.model;

import java.util.List;

public class Order {

    public Long id;
    public List<Position> positions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return "Order{" +
                "\n\t\t\tid='" + id + '\'' +
                ", \n\t\t\tpositions=" + positions +
                '}';
    }
}
