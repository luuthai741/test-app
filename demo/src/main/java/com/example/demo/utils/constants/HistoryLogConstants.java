package com.example.demo.utils.constants;

public class HistoryLogConstants {
    public static final String SELECT_ALL = "SELECT * FROM history_log ";
    public static final String INSERT_LOG = "INSERT INTO history_log(LOG_TYPE,CONTENT,ACTION,CREATED_AT) " +
            "VALUES ('%s','%s','%s','%s')";
}
