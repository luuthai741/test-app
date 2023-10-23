package com.example.demo.mapper;

import com.example.demo.dao.OrderDAO;
import com.example.demo.model.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    private static OrderMapper instance;

    private OrderMapper() {
    }

    public static OrderMapper getInstance() {
        if (instance == null) {
            instance = new OrderMapper();
        }
        return instance;
    }

    public List<Order> mapToOrder(ResultSet resultSet) throws SQLException {
        List<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            Order order = new Order();
            order.setId(resultSet.getLong("ID"));
            order.setIndex(resultSet.getInt("INDEX_BY_DAY"));
            order.setLicensePlates(resultSet.getString("LICENSE_PLATES"));
            order.setSeller(resultSet.getString("SELLER"));
            order.setBuyer(resultSet.getString("BUYER"));
            order.setTotalWeight(resultSet.getInt("TOTAL_WEIGHT"));
            order.setVehicleWeight(resultSet.getInt("VEHICLE_WEIGHT"));
            order.setCargoWeight(resultSet.getInt("CARGO_WEIGHT"));
            order.setStatus(resultSet.getString("STATUS"));
            order.setPaymentStatus(resultSet.getString("PAYMENT_STATUS"));
            order.setCargoType(resultSet.getString("CARGO_TYPE"));
            order.setPaymentAmount(resultSet.getDouble("PAYMENT_AMOUNT"));
            order.setNote(resultSet.getString("NOTE"));
            order.setPayer(resultSet.getString("PAYER"));
            order.setCreatedBy(resultSet.getString("CREATED_BY"));
            orders.add(order);
        }
        return orders;
    }

    public long countOrder(ResultSet resultSet) throws SQLException {
        long total = 0;
        if (resultSet.next()) {
            total = resultSet.getLong("total");
        }
        return total;
    }
}
