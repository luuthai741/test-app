package com.example.demo.utils.constants;

public class OrderQueryConstants {
    public static final String SELECT_ALL = "SELECT * FROM order ";
    public static final String COUNT_ALL = "SELECT COUNT(*) AS total FROM order ";
    public static final String INSERT_ORDER = "INSERT INTO order(INDEX_BY_DAY,LICENSE_PLATES,SELLER,BUYER,TOTAL_WEIGHT,VEHICLE_WEIGHT,CARGO_WEIGHT,CREATED_AT,UPDATED_AT,STATUS,PAYMENT_STATUS,CARGO_TYPE,PAYMENT_AMOUNT,NOTE,PAYER,CREATED_BY) " +
            "VALUE (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)";
    public static final String UPDATE_ORDER_BY_ID = "UPDATE order SET LICENSE_PLATES = %s,SELLER,BUYER = %s,TOTAL_WEIGHT = %s,VEHICLE_WEIGHT = %s,CARGO_WEIGHT = %s,UPDATED_AT = %s,STATUS = %s,PAYMENT_STATUS = %s,CARGO_TYPE = %s,PAYMENT_AMOUNT = %s,NOTE = %s,PAYER = %s " +
            "WHERE ID = %s";
    public static final String UPDATE_ORDER_STATUS_BY_ID = "UPDATE order STATUS = %s WHERE ID = %s";
}
