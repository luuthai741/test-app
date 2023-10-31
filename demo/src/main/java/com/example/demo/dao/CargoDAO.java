package com.example.demo.dao;

import com.example.demo.mapper.CargoMapper;
import com.example.demo.model.Cargo;
import com.example.demo.utils.util.SqlUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.utils.constants.CargoConstants.*;


public class CargoDAO {
    private static CargoDAO instance;
    private CargoMapper cargoMapper = CargoMapper.getInstance();

    private CargoDAO() {
    }

    public static CargoDAO getInstance() {
        if (instance == null) {
            instance = new CargoDAO();
        }
        return instance;
    }

    public ObservableList<String> getAll() {
        SqlUtil sqlUtil = new SqlUtil();
        ObservableList<String> cargoObservableList = FXCollections.observableList(new ArrayList<>());
        try {
            sqlUtil.connect();
            String sql = SELECT_ALL;
            ResultSet rs = sqlUtil.exeQuery(sql);
            List<Cargo> cargos = cargoMapper.mapToCargo(rs);
            cargoObservableList = FXCollections.observableList(cargos.stream().map(Cargo::getName).collect(Collectors.toList()));
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return cargoObservableList;
    }

    public void createCargo(String cargoName) {
        cargoName = cargoName.trim();
        Cargo cargoDb = getByName(cargoName);
        if (cargoDb != null) {
            return;
        }
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String sql = String.format(INSERT_CARGO, cargoName);
            sqlUtil.exeUpdate(sql);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
    }

    public Cargo getByName(String cargoName) {
        Cargo cargo = null;
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String sql = String.format(SELECT_BY_NAME, cargoName);
            ResultSet rs = sqlUtil.exeQuery(sql);
            List<Cargo> cargos = cargoMapper.mapToCargo(rs);
            if (cargos.size() > 0) {
                cargo = cargos.get(0);
            }
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return cargo;
    }
}
