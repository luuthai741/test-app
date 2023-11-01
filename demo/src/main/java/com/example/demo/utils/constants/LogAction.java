package com.example.demo.utils.constants;

import com.example.demo.data.CurrentUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.utils.constants.RoleType.USER;

public enum LogAction {
    ALL("Tất cả"),
    CREATED_ORDER("Tạo mã cân"),
    CREATED_ORDER_MANUAL("Tạo mã cân thủ công"),
    UPDATED_ORDER("Cập nhật mã cân"),
    UPDATED_ORDER_MANUAL("Cập nhật mã cân thủ công"),
    DELETE_ORDER("Xóa mã cân"),
    UPDATED_PAID_ORDERS("Cập nhật thanh toán mã cân");

    private String note;

    LogAction(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public static ObservableList<String> getActions() {
        List<String> actions = new ArrayList<>();
        for (LogAction action : LogAction.values()) {
            actions.add(action.getNote());
        }
        return FXCollections.observableList(actions);
    }

    public static String convertListToInClause() {
        StringBuilder builder = new StringBuilder();
        String syntax = getActions().stream().map(str -> String.format("'%s'", str)).collect(Collectors.joining(","));
        builder.append("(").append(syntax).append(")");
        return builder.toString();
    }
}
