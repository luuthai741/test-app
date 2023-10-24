package com.example.demo.utils.constants;

public enum Page {
    ADMIN_HOME("admin/home.fxml"),
    LIST("general/list.fxml"),
    DEBTOR("admin/debtor.fxml"),
    INDEX("general/index.fxml"),
    ORDER("general/order.fxml"),
    REPORT("general/report.fxml"),
    FORM("general/form.fxml"),
    SETTING("admin/setting.fxml"),
    WEIGHT_MONEY("admin/weight_money.fxml");

    private String fxml;

    Page(String fxml) {
        this.fxml = fxml;
    }

    public String getFxml() {
        return fxml;
    }
}
