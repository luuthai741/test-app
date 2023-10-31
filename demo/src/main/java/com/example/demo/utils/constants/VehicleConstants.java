package com.example.demo.utils.constants;

public class VehicleConstants {
    public static final String SELECT_ALL = "SELECT * FROM vehicle";
    public static final String INSERT_VEHICLE = "INSERT INTO vehicle(NAME,PATTERN) VALUES ('%s','%s')";
    public static final String UPDATE_VEHICLE_BY_ID = "UPDATE vehicle SET NAME = '%s', PATTERN = '%s' WHERE ID = %s";
    public static final String DELETE_VEHICLE_BY_ID = "DELETE vehicle WHERE ID = '%s'";
}
