package com.example.demo.mapper;

import com.example.demo.model.HistoryLog;
import com.example.demo.model.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HistoryLogMapper {
    private static HistoryLogMapper instance;

    private HistoryLogMapper() {
    }

    public static HistoryLogMapper getInstance() {
        if (instance == null) {
            instance = new HistoryLogMapper();
        }
        return instance;
    }

    public List<HistoryLog> mapToLog(ResultSet resultSet) throws SQLException {
        List<HistoryLog> historyLogs = new ArrayList<>();
        while (resultSet.next()) {
            HistoryLog historyLog = new HistoryLog();
            historyLog.setId(resultSet.getLong("ID"));
            historyLog.setLogType(resultSet.getString("LOG_TYPE"));
            historyLog.setContent(resultSet.getString("CONTENT"));
            historyLog.setAction(resultSet.getString("ACTION"));
            LocalDateTime createdAt = null;
            if (resultSet.getDate("CREATED_AT") != null) {
                LocalDate date = resultSet.getDate("CREATED_AT").toLocalDate();
                LocalTime time = resultSet.getTime("CREATED_AT").toLocalTime();
                createdAt = LocalDateTime.of(date, time);
            }
            historyLog.setCreatedAt(createdAt);
            historyLogs.add(historyLog);
        }
        return historyLogs;
    }
}
