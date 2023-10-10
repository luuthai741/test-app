package com.example.demo.dao;

import com.example.demo.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private static OrderDAO instance;

    private OrderDAO() {
    }

    private ObservableList<Order> orders;

    public static OrderDAO getInstance() {
        if (instance == null) {
            instance = new OrderDAO();
        }
        return instance;
    }

    public ObservableList<Order> getOrders() {
        if (orders != null && !orders.isEmpty()) {
            return orders;
        }
        List<Order> orderList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            orderList.add(new Order(i, i, "99c33737", "TESTER SELLER", "TESTER BUYER", 1000, 0, 0, LocalDate.now(), LocalDate.now(), "CREATED", "UNPAID", "", 0, "", "", ""));
        }
        orders = FXCollections.observableList(orderList);
        return orders;
    }
}
