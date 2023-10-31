package com.example.demo.service;

public class PaymentService {
    private static PaymentService instance;

    private PaymentService() {
    }

    public PaymentService getInstance() {
        if (instance == null) {
            instance = new PaymentService();
        }
        return instance;
    }

}
