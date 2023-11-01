package com.example.demo.dao;

import com.example.demo.mapper.WeightMoneyMapper;
import com.example.demo.model.Vehicle;
import com.example.demo.model.WeightMoney;
import com.example.demo.utils.util.SqlUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.lang.StringUtils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.utils.constants.WeightMoneyConstants.*;


public class WeightMoneyDAO {
    private static WeightMoneyDAO instance;
    private VehicleDAO vehicleDAO = VehicleDAO.getInstance();
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
            String sql = String.format(INSERT_WEIGHT_MONEY, weightMoney.getStartWeight(), weightMoney.getEndWeight(), weightMoney.getAmountMoney(), weightMoney.getVehicleId(), weightMoney.getMinAmount());
            sqlUtil.exeUpdate(sql);
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
            String sql = String.format(UPDATE_WEIGHT_MONEY_BY_ID, weightMoney.getStartWeight(), weightMoney.getEndWeight(), weightMoney.getAmountMoney(), weightMoney.getVehicleId(), weightMoney.getMinAmount(), weightMoney.getId());
            sqlUtil.exeUpdate(sql);
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
            sqlUtil.exeUpdate(sql);
            isSuccessFully = true;
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return isSuccessFully;
    }

    public double getAmountByCargoWeight(String licensePlatesText, int cargoWeight, boolean isFirstTime) {
        if (StringUtils.isBlank(licensePlatesText)){
            return 0;
        }
        SqlUtil sqlUtil = new SqlUtil();
        double amount = 0;
        double minAmount = 0;
        ObservableList<Vehicle> vehicles = vehicleDAO.getAll();
        int vehicleId = 0;
        for (Vehicle vehicle : vehicles) {
            if (licensePlatesText.equalsIgnoreCase(vehicle.getPattern()) || licensePlatesText.matches(vehicle.getPattern())) {
                vehicleId = vehicle.getId();
                break;
            }
        }
        try {
            sqlUtil.connect();
            String sql = String.format(SELECT_BETWEEN_START_AND_END_AND_VEHICLE_ID, cargoWeight, cargoWeight, vehicleId);
            ResultSet resultSet = sqlUtil.exeQuery(sql);
            List<WeightMoney> moneyList = weightMoneyMapper.mapToWeightMoney(resultSet);
            if (moneyList.size() > 0) {
                amount = moneyList.get(0).getAmountMoney();
                minAmount = moneyList.get(0).getMinAmount();
            }
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        amount = isFirstTime ? minAmount : amount;
        return amount;
    }

    public int countWeightMoney() {
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String sql = COUNT_ALL;
            ResultSet rs = sqlUtil.exeQuery(sql);
            return weightMoneyMapper.countWeightMoney(rs);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return 0;
    }
}
