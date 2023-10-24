package com.example.demo.query;

import com.example.demo.utils.constants.OrderStatus;
import com.example.demo.utils.constants.PaymentStatus;
import com.example.demo.utils.util.DateUtil;

import java.time.LocalDateTime;
import java.util.Date;

import static com.example.demo.utils.constants.OrderStatus.ALL;

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
        if (index != null && !index.isBlank()) {
            query = " AND INDEX_BY_DAY = " + index;
        }
        return query;
    }

    private String queryByLicensePlates() {
        String query = "";
        if (licensePlates != null && !licensePlates.isBlank()) {
            query = " AND LICENSE_PLATES = '*" + licensePlates + "*'";
        }
        return query;
    }

    private String queryBySeller() {
        String query = "";
        if (seller != null && !seller.isBlank()) {
            seller = seller.trim();
            String[] parts = seller.split(" ");
            if (parts.length == 2) {
                String seller1 = parts[0] + " " + parts[1];
                String seller2 = parts[1] + " " + parts[2];
                query = " AND (SELLER = '*" + seller1 + "*' OR SELLER = '*" + seller2 + "'";
            } else {
                query = " AND SELLER = '*" + seller + "*'";
            }
        }
        return query;
    }

    private String queryByBuyer() {
        String query = "";
        if (buyer != null && !buyer.isBlank()) {
            buyer = buyer.trim();
            String[] parts = buyer.split(" ");
            if (parts.length == 2) {
                String buyer1 = parts[0] + " " + parts[1];
                String buyer2 = parts[1] + " " + parts[2];
                query = " AND (BUYER = '*" + buyer1 + "*' OR BUYER = '*" + buyer1 + "'";
            } else {
                query = " AND BUYER = '*" + seller + "*'";
            }
        }
        return query;
    }

    private String queryByStatus() {
        String query = "";
        if (status != null && !status.isBlank() && !status.equals(ALL.getNote())) {
            query = " AND STATUS = '" + status + "'";
        }
        return query;
    }

    private String queryByPaymentStatus() {
        String query = "";
        if (paymentStatus != null && !paymentStatus.isBlank() && !paymentStatus.equals(PaymentStatus.ALL.getNote())) {
            query = " AND PAYMENT_STATUS = '" + paymentStatus + "'";
        }
        return query;
    }


    private String queryByCreatedAt() {
        String query = "";
        if (dateTimeFrom == null) {
            dateTimeFrom = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        }
        if (dateTimeTo == null) {
            dateTimeTo = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        }
        String dateFromQuery = DateUtil.convertToString(dateTimeFrom, DateUtil.DB_FORMAT);
        String dateToQuery = DateUtil.convertToString(dateTimeTo, DateUtil.DB_FORMAT);
        query = " AND CREATED_AT >= '" + dateFromQuery + "' AND CREATED_AT <= '" + dateToQuery + "'";
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
