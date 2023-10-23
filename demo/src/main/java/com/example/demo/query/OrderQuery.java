package com.example.demo.query;

import com.example.demo.utils.util.DateUtil;

import java.time.LocalDateTime;
import java.util.Date;

public class OrderQuery {
    private String index;
    private String licensePlates;
    private String seller;
    private String buyer;
    private String status;
    private String paymentStatus;
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;
    private int limit;

    private String queryByIndex() {
        String query = "";
        if (!index.isBlank()) {
            query = " AND INDEX_BY_DAY = " + index;
        }
        return query;
    }

    private String queryByLicensePlates() {
        String query = "";
        if (!licensePlates.isBlank()) {
            query = " AND LICENSE_PLATES = " + licensePlates;
        }
        return query;
    }

    private String queryBySeller() {
        String query = "";
        if (!seller.isBlank()) {
            query = " AND SELLER = " + seller;
        }
        return query;
    }

    private String queryByBuyer() {
        String query = "";
        if (!buyer.isBlank()) {
            query = " AND BUYER = " + buyer;
        }
        return query;
    }

    private String queryByStatus() {
        String query = "";
        if (!status.isBlank()) {
            query = " AND STATUS = " + status;
        }
        return query;
    }

    private String queryByPaymentStatus() {
        String query = "";
        if (!paymentStatus.isBlank()) {
            query = " AND PAYMENT_STATUS = " + paymentStatus;
        }
        return query;
    }


    private String queryByCreatedAt() {
        String query = "";
        if (dateTimeFrom == null) {
            dateTimeFrom = LocalDateTime.now();
        }
        if (dateTimeTo == null) {
            dateTimeTo = LocalDateTime.now();
        }
        String dateFromQuery = DateUtil.convertToString(dateTimeFrom, DateUtil.DB_FORMAT);
        String dateToQuery = DateUtil.convertToString(dateTimeTo, DateUtil.DB_FORMAT);
        query = " AND CREATED_AT >= " + dateFromQuery + " AND CREATED_AT <= " + dateToQuery;
        return query;
    }

    public String getQueryCondition() {
        return " WHERE 1=1 "
                + queryByIndex()
                + queryByLicensePlates()
                + queryBySeller()
                + queryByBuyer()
                + queryByStatus()
                + queryByPaymentStatus()
                + queryByCreatedAt();
    }

    public OrderQuery() {
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
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

    public LocalDateTime getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(LocalDateTime dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public LocalDateTime getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(LocalDateTime dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }
}
