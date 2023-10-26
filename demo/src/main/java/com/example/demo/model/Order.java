package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order implements Cloneable {
    private long id;
    private int index;
    private String licensePlates;
    private String seller;
    private String buyer;
    private int totalWeight;
    private int vehicleWeight;
    private int cargoWeight;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
    private String paymentStatus;
    private String cargoType;
    private double paymentAmount;
    private String note;
    private String payer;
    private String createdBy;

    public Order() {
    }

    public Order(long id, int index, String licensePlates, String seller, String buyer, int totalWeight, int vehicleWeight, int cargoWeight, LocalDateTime createdAt, LocalDateTime updatedAt, String status, String paymentStatus, String cargoType, double paymentAmount, String note, String payer, String createdBy) {
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

    public int getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    public int getVehicleWeight() {
        return vehicleWeight;
    }

    public void setVehicleWeight(int vehicleWeight) {
        this.vehicleWeight = vehicleWeight;
    }

    public int getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(int cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
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

    public Order clone() throws CloneNotSupportedException {
        return (Order) super.clone();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", index=" + index +
                ", licensePlates='" + licensePlates + '\'' +
                ", seller='" + seller + '\'' +
                ", buyer='" + buyer + '\'' +
                ", totalWeight=" + totalWeight +
                ", vehicleWeight=" + vehicleWeight +
                ", cargoWeight=" + cargoWeight +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", status='" + status + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", cargoType='" + cargoType + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", note='" + note + '\'' +
                ", payer='" + payer + '\'' +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }

    public static String reportHeader() {
        return String.format("%s-20s%s-20s%s-20s%s", "STT", "Biển số xe", "Người bán", "Người mua");
    }

    public String reportBody() {
        return String.format("%s-20s%s-20s%s-20s%s", index, licensePlates, seller, buyer);
    }
}
