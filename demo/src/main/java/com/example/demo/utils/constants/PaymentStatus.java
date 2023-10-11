package com.example.demo.utils.constants;

public enum PaymentStatus {
    PAID("Đã trả"),
    UNPAID("Chưa trả");

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
}
