package com.example.demo.utils.constants;

import com.example.demo.data.CurrentUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.utils.constants.RoleType.ADMIN;
import static com.example.demo.utils.constants.RoleType.USER;

public enum OrderStatus {
    ALL("Tất cả"),
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

    public static ObservableList<String> getIndexStatus() {
        List<String> statusList = new ArrayList<>();
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (OrderStatus.CANCELED.equals(orderStatus) && CurrentUser.getInstance().getRole().equals(USER)) {
                continue;
            }
            statusList.add(orderStatus.getNote());
        }
        return FXCollections.observableList(statusList);
    }
}
