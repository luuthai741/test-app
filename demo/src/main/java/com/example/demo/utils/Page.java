package com.example.demo.utils;

public enum Page {
    ADMIN_HOME("admin/home.fxml"),
    LIST("general/list.fxml"),
    DEBTOR("admin/debtor.fxml"),
    INDEX("general/index.fxml"),
    FORM("general/form.fxml");
    private String fxml;

    Page(String fxml) {
        this.fxml = fxml;
    }

    public String getFxml() {
        return fxml;
    }
}
