package com.example.demo.utils.constants;

public class WeightMoneyConstants {
    public static final String SELECT_ALL = "SELECT * FROM weight_money";
    public static final String COUNT_ALL = "SELECT COUNT(*) AS total FROM weight_money";
    public static final String SELECT_BETWEEN_START_AND_END_AND_TYPE = "SELECT TOP 1 * FROM weight_money WHERE END_WEIGHT >= '%s' AND START_WEIGHT <= '%s' AND TYPE = '%s'";
    public static final String INSERT_WEIGHT_MONEY = "INSERT INTO weight_money(START_WEIGHT, END_WEIGHT, AMOUNT_MONEY, TYPE) VALUES ('%s','%s','%s','%s')";
    public static final String UPDATE_WEIGHT_MONEY_BY_ID = "UPDATE weight_money SET START_WEIGHT = '%s', END_WEIGHT = '%s', AMOUNT_MONEY = '%s', TYPE = '%s' WHERE ID = '%s'";

    public static final String DELETE_WEIGHT_MONEY_BY_ID = "DELETE weight_money WHERE ID = '%s'";
}
