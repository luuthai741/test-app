package com.example.demo.utils.constants;

import java.util.ArrayList;
import java.util.List;

public enum VehicleType {
    CAR,
    FARM,
    HANDCART,
    OTHER;

    public static List<String> getVehicleTypes(){
        List<String> vehicleTypes = new ArrayList<>();
        for (VehicleType vehicleType : VehicleType.values()){
            vehicleTypes.add(vehicleType.name());
        }
        return vehicleTypes;
    }
}
