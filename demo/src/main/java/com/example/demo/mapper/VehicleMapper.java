package com.example.demo.mapper;

import com.example.demo.model.Cargo;
import com.example.demo.model.Vehicle;
import org.apache.commons.lang.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleMapper {
    private static VehicleMapper instance;

    private VehicleMapper() {
    }

    public static VehicleMapper getInstance() {
        if (instance == null) {
            instance = new VehicleMapper();
        }
        return instance;
    }

    public List<Vehicle> mapToVehicle(ResultSet resultSet) throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        while (resultSet.next()) {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(resultSet.getInt("ID"));
            vehicle.setName(resultSet.getString("NAME"));
            vehicle.setPattern(resultSet.getString("PATTERN"));
            vehicles.add(vehicle);
        }
        return vehicles;
    }
}
