package com.example.demo.mapper;

import com.example.demo.model.WeightMoney;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WeightMoneyMapper {
    private static WeightMoneyMapper instance;

    private WeightMoneyMapper() {
    }

    public static WeightMoneyMapper getInstance() {
        if (instance == null) {
            instance = new WeightMoneyMapper();
        }
        return instance;
    }

    public List<WeightMoney> mapToWeightMoney(ResultSet resultSet) throws SQLException {
        List<WeightMoney> moneyList = new ArrayList<>();
        while (resultSet.next()) {
            WeightMoney weightMoney = new WeightMoney();
            weightMoney.setStartWeight(resultSet.getInt("START_WEIGHT"));
            weightMoney.setEndWeight(resultSet.getInt("END_WEIGHT"));
            weightMoney.setAmountMoney(resultSet.getInt("AMOUNT_MONEY"));
            weightMoney.setType(resultSet.getString("TYPE"));
            moneyList.add(weightMoney);
        }
        return moneyList;
    }
}
