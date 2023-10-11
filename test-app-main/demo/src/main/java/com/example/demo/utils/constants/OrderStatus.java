package com.example.demo.utils.constants;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public enum OrderStatus {
    CREATED("Vừa tạo"),
    COMPLETED("Hoàn thành"),
    CANCELED("Hủy");
    private String note;

    OrderStatus(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public static OrderStatus getByNote(String note) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.getNote().equals(note)) {
                return orderStatus;
            }
        }
        return null;
    }

    public static ObservableList<String> getIndexStatus(){
        List<String> statusList = new ArrayList<>();
        for (OrderStatus orderStatus : OrderStatus.values()) {
            statusList.add(orderStatus.getNote());
        }
        return FXCollections.observableList(statusList);
    }
}
