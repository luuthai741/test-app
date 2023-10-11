package com.example.demo.utils.constants;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public enum PaymentStatus {
    PAID("Trả"),
    UNPAID("Nợ");

    private String note;

    PaymentStatus(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public static PaymentStatus getByNote(String note) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.getNote().equals(note)) {
                return paymentStatus;
            }
        }
        return null;
    }

    public static ObservableList<String> getIndexStatus(){
        List<String> statusList = new ArrayList<>();
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            statusList.add(paymentStatus.getNote());
        }
        return FXCollections.observableList(statusList);
    }
}
