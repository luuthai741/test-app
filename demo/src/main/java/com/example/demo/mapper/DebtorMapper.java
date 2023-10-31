package com.example.demo.mapper;

import com.example.demo.model.Debtor;
import com.example.demo.model.WeightMoney;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DebtorMapper {
    private static DebtorMapper instance;

    private DebtorMapper() {
    }

    public static DebtorMapper getInstance() {
        if (instance == null) {
            instance = new DebtorMapper();
        }
        return instance;
    }

    public List<Debtor> mapToDebtor(ResultSet resultSet) throws SQLException {
        List<Debtor> debtors = new ArrayList<>();
        while (resultSet.next()) {
            Debtor debtor = new Debtor();
            debtor.setId(resultSet.getInt("ID"));
            debtor.setDebtor(resultSet.getString("DEBTOR"));
            debtor.setStatus(resultSet.getString("STATUS"));
            debtor.setMonth(resultSet.getInt("MONTH"));
            debtor.setTotalAmount(resultSet.getDouble("TOTAL_AMOUNT"));
            debtor.setOrderIds(resultSet.getString("ORDER_IDS"));
            LocalDateTime createdAt = null;
            LocalDateTime updatedAt = null;
            if (resultSet.getDate("CREATED_AT") != null) {
                LocalDate date = resultSet.getDate("CREATED_AT").toLocalDate();
                LocalTime time = resultSet.getTime("CREATED_AT").toLocalTime();
                createdAt = LocalDateTime.of(date, time);
            }
            if (resultSet.getDate("UPDATED_AT") != null) {
                LocalDate date = resultSet.getDate("UPDATED_AT").toLocalDate();
                LocalTime time = resultSet.getTime("UPDATED_AT").toLocalTime();
                updatedAt = LocalDateTime.of(date, time);
            }
            debtor.setCreatedAt(createdAt);
            debtor.setUpdatedAt(updatedAt);
            debtors.add(debtor);
        }
        return debtors;
    }

    public int countWeightMoney(ResultSet resultSet) throws SQLException {
        int total = 0;
        if (resultSet.next()) {
            total = resultSet.getInt("total");
        }
        return total;
    }
}
