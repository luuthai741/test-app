package com.example.demo.model;

import java.time.LocalDate;

public class Order {
    private long id;
    private int index;
    private String licensePlates;
    private String seller;
    private String buyer;
    private double totalWeight;
    private double vehicleWeight;
    private double cargoWeight;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String status;
    private String paymentStatus;
    private String cargoType;
    private double paymentAmount;
    private String note;
    private String payer;
    private String createdBy;

    public Order() {
    }

    public Order(long id, int index, String licensePlates, String seller, String buyer, double totalWeight, double vehicleWeight, double cargoWeight, LocalDate createdAt, LocalDate updatedAt, String status, String paymentStatus, String cargoType, double paymentAmount, String note, String payer, String createdBy) {
        this.id = id;
        this.index = index;
        this.licensePlates = licensePlates;
        this.seller = seller;
        this.buyer = buyer;
        this.totalWeight = totalWeight;
        this.vehicleWeight = vehicleWeight;
        this.cargoWeight = cargoWeight;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.cargoType = cargoType;
        this.paymentAmount = paymentAmount;
        this.note = note;
        this.payer = payer;
        this.createdBy = createdBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getLicensePlates() {
        return licensePlates;
    }

    public void setLicensePlates(String licensePlates) {
        this.licensePlates = licensePlates;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public double getVehicleWeight() {
        return vehicleWeight;
    }

    public void setVehicleWeight(double vehicleWeight) {
        this.vehicleWeight = vehicleWeight;
    }

    public double getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCargoType() {
        return cargoType;
    }

    public void setCargoType(String cargoType) {
        this.cargoType = cargoType;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
