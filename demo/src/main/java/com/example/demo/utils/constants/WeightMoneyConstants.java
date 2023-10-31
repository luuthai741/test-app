package com.example.demo.utils.constants;

public class WeightMoneyConstants {
    public static final String SELECT_ALL = "SELECT * FROM weight_money";
    public static final String COUNT_ALL = "SELECT COUNT(*) AS total FROM weight_money";
    public static final String SELECT_BETWEEN_START_AND_END_AND_VEHICLE_ID = "SELECT TOP 1 * FROM weight_money WHERE END_WEIGHT >= '%s' AND START_WEIGHT <= '%s' AND VEHICLE_ID = '%s'";
    public static final String INSERT_WEIGHT_MONEY = "INSERT INTO weight_money(START_WEIGHT, END_WEIGHT, AMOUNT_MONEY, VEHICLE_ID, MIN_AMOUNT) VALUES ('%s','%s','%s','%s','%s')";
    public static final String UPDATE_WEIGHT_MONEY_BY_ID = "UPDATE weight_money SET START_WEIGHT = '%s', END_WEIGHT = '%s', AMOUNT_MONEY = '%s', VEHICLE_ID = '%s', MIN_AMOUNT = '%s' WHERE ID = '%s' ";

    public static final String DELETE_WEIGHT_MONEY_BY_ID = "DELETE weight_money WHERE ID = '%s'";
}
