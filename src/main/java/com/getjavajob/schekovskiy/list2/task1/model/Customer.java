package com.getjavajob.schekovskiy.list2.task1.model;

import java.util.List;

public class Customer {

    public Long id;
    public String name;
    public List<Order> orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customer{ " +
                "\n\tid='" + id + '\'' +
                ", \n\tname='" + name + '\'' +
                ", \n\torders=" + orders +
                '}';
    }
}
