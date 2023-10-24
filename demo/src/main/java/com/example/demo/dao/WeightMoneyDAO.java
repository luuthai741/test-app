package com.example.demo.dao;

import com.example.demo.mapper.WeightMoneyMapper;
import com.example.demo.model.WeightMoney;
import com.example.demo.utils.constants.VehicleType;
import com.example.demo.utils.util.SqlUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.utils.constants.VehicleType.*;
import static com.example.demo.utils.constants.WeightMoneyConstants.*;


public class WeightMoneyDAO {
    private static WeightMoneyDAO instance;
    private WeightMoneyMapper weightMoneyMapper = WeightMoneyMapper.getInstance();

    private WeightMoneyDAO() {
    }

    public static WeightMoneyDAO getInstance() {
        if (instance == null) {
            instance = new WeightMoneyDAO();
        }
        return instance;
    }

    public ObservableList<WeightMoney> getAll() {
        SqlUtil sqlUtil = new SqlUtil();
        ObservableList<WeightMoney> moneyObservableList = FXCollections.observableList(new ArrayList<>());
        try {
            sqlUtil.connect();
            String sql = SELECT_ALL;
            ResultSet rs = sqlUtil.exeQuery(sql);
            List<WeightMoney> moneyList = weightMoneyMapper.mapToWeightMoney(rs);
            moneyObservableList = FXCollections.observableList(moneyList);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return moneyObservableList;
    }

    public WeightMoney createWeightMoney(WeightMoney weightMoney) {
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String sql = String.format(INSERT_WEIGHT_MONEY, weightMoney.getStartWeight(), weightMoney.getEndWeight(), weightMoney.getAmountMoney(), weightMoney.getType());
            sqlUtil.exeQuery(sql);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return weightMoney;
    }

    public WeightMoney updateWeightMoney(WeightMoney weightMoney) {
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String sql = String.format(UPDATE_WEIGHT_MONEY_BY_ID, weightMoney.getStartWeight(), weightMoney.getEndWeight(), weightMoney.getAmountMoney(), weightMoney.getType(), weightMoney.getId());
            sqlUtil.exeQuery(sql);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return weightMoney;
    }

    public boolean deleteWeightMoneyById(int id) {
        SqlUtil sqlUtil = new SqlUtil();
        boolean isSuccessFully = false;
        try {
            sqlUtil.connect();
            String sql = String.format(DELETE_WEIGHT_MONEY_BY_ID, id);
            sqlUtil.exeQuery(sql);
            isSuccessFully = true;
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return isSuccessFully;
    }

    public double getAmountByCargoWeight(String licensePlatesText, int cargoWeight) {
        SqlUtil sqlUtil = new SqlUtil();
        double amount = 0;
        VehicleType vehicleType = OTHER;
        if (licensePlatesText.equalsIgnoreCase("cn")) {
            vehicleType = FARM;
        } else if (licensePlatesText.equalsIgnoreCase("xk")) {
            vehicleType = HANDCART;
        } else if (licensePlatesText.matches("([a-zA-Z])([0-9]+)")) {
            vehicleType = CAR;
        }
        try {
            sqlUtil.connect();
            String sql = String.format(SELECT_BETWEEN_START_AND_END_AND_TYPE, cargoWeight, cargoWeight, vehicleType.name());
            ResultSet resultSet = sqlUtil.exeQuery(sql);
            List<WeightMoney> moneyList = weightMoneyMapper.mapToWeightMoney(resultSet);
            if (moneyList.size() > 0) {
                amount = moneyList.get(0).getAmountMoney();
            }
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return amount;
    }
}
