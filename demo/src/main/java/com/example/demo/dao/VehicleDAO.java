package com.example.demo.dao;

import com.example.demo.mapper.VehicleMapper;
import com.example.demo.model.Vehicle;
import com.example.demo.model.WeightMoney;
import com.example.demo.utils.util.SqlUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.utils.constants.VehicleConstants.*;
import static com.example.demo.utils.constants.WeightMoneyConstants.UPDATE_WEIGHT_MONEY_BY_ID;


public class VehicleDAO {
    private static VehicleDAO instance;
    private VehicleMapper vehicleMapper = VehicleMapper.getInstance();

    private VehicleDAO() {
    }

    public static VehicleDAO getInstance() {
        if (instance == null) {
            instance = new VehicleDAO();
        }
        return instance;
    }

    public ObservableList<Vehicle> getAll(){
        SqlUtil sqlUtil = new SqlUtil();
        ObservableList<Vehicle> vehicleObservableList = FXCollections.observableList(new ArrayList<>());
        try {
            sqlUtil.connect();
            String sql = SELECT_ALL;
            ResultSet rs = sqlUtil.exeQuery(sql);
            List<Vehicle> vehicles = vehicleMapper.mapToVehicle(rs);
            vehicleObservableList = FXCollections.observableList(vehicles);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return vehicleObservableList;
    }

    public Vehicle createVehicle(Vehicle vehicle) {
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String sql = String.format(INSERT_VEHICLE, vehicle.getName(), vehicle.getPattern());
            sqlUtil.exeUpdate(sql);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return vehicle;
    }

    public Vehicle updateVehicle(Vehicle vehicle) {
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String sql = String.format(UPDATE_VEHICLE_BY_ID, vehicle.getName(), vehicle.getPattern(), vehicle.getId());
            sqlUtil.exeUpdate(sql);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return vehicle;
    }
}
