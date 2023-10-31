package com.example.demo.mapper;

import com.example.demo.model.Cargo;
import com.example.demo.model.WeightMoney;
import org.apache.commons.lang.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CargoMapper {
    private static CargoMapper instance;

    private CargoMapper() {
    }

    public static CargoMapper getInstance() {
        if (instance == null) {
            instance = new CargoMapper();
        }
        return instance;
    }

    public List<Cargo> mapToCargo(ResultSet resultSet) throws SQLException {
        List<Cargo> cargos = new ArrayList<>();
        while (resultSet.next()) {
            String cargoName = resultSet.getString("NAME");
            if (StringUtils.isBlank(cargoName)){
                continue;
            }
            Cargo cargo = new Cargo();
            cargo.setName(cargoName);
            cargo.setId(resultSet.getInt("ID"));
            cargos.add(cargo);
        }
        return cargos;
    }
}
