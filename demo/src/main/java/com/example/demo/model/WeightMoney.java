package com.example.demo.model;

public class WeightMoney {
    private int id;
    private int startWeight;
    private int endWeight;
    private double amountMoney;
    private double minAmount;
    private int vehicleId;
    public WeightMoney() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(int startWeight) {
        this.startWeight = startWeight;
    }

    public int getEndWeight() {
        return endWeight;
    }

    public void setEndWeight(int endWeight) {
        this.endWeight = endWeight;
    }

    public double getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(double amountMoney) {
        this.amountMoney = amountMoney;
    }


    public double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(double minAmount) {
        this.minAmount = minAmount;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
}
