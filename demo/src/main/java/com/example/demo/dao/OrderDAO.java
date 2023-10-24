package com.example.demo.dao;

import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.Order;
import com.example.demo.query.OrderQuery;
import com.example.demo.utils.util.DateUtil;
import com.example.demo.utils.util.SqlUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.utils.constants.OrderQueryConstants.*;
import static com.example.demo.utils.constants.OrderStatus.CANCELED;
import static com.example.demo.utils.constants.OrderStatus.CREATED;

public class OrderDAO {
    private static OrderDAO instance;

    private OrderDAO() {
    }

    private OrderMapper orderMapper = OrderMapper.getInstance();

    public static OrderDAO getInstance() {
        if (instance == null) {
            instance = new OrderDAO();
        }
        return instance;
    }

    public long countOrderByDate(LocalDateTime dateTime) {
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.setDateTimeFrom(dateTime.withHour(0).withMinute(0).withSecond(0));
        orderQuery.setDateTimeTo(dateTime.withHour(23).withMinute(59).withSecond(59));
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String sql = COUNT_ALL + orderQuery.getQueryCondition();
            ResultSet rs = sqlUtil.exeQuery(sql);
            return orderMapper.countOrder(rs);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return 0;
    }

    public long countById() {
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String sql = COUNT_ALL;
            ResultSet rs = sqlUtil.exeQuery(sql);
            return orderMapper.countOrder(rs);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return 0;
    }

    public Order getOrderByIndexAndDate(int index, LocalDateTime dateTime) {
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.setIndex(String.valueOf(index));
        orderQuery.setDateTimeFrom(dateTime.withHour(0).withMinute(0).withSecond(0));
        orderQuery.setDateTimeTo(dateTime.withHour(23).withMinute(59).withSecond(59));
        SqlUtil sqlUtil = new SqlUtil();
        Order order = null;
        try {
            sqlUtil.connect();
            String sql = SELECT_ALL + orderQuery.getQueryCondition();
            ResultSet rs = sqlUtil.exeQuery(sql);
            List<Order> orderList = orderMapper.mapToOrder(rs);
            if (orderList.size() > 0) {
                order = orderList.get(0);
            }
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return order;
    }

    public ObservableList<Order> getOrders() {
        SqlUtil sqlUtil = new SqlUtil();
        ObservableList<Order> orders = FXCollections.observableList(new ArrayList<>());
        try {
            sqlUtil.connect();
            OrderQuery orderQuery = new OrderQuery();
            orderQuery.setStatus(CREATED.getNote());
            String sql = SELECT_ALL + orderQuery.getQueryCondition();
            ResultSet rs = sqlUtil.exeQuery(sql);
            List<Order> orderList = orderMapper.mapToOrder(rs);
            orders = FXCollections.observableList(orderList);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return orders;
    }

    public ObservableList<Order> getOrderFilters(String licensePlates, String seller, String buyer, String status, String paymentStatus, LocalDateTime dateFrom, LocalDateTime dateTo) {
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.setLicensePlates(licensePlates);
        orderQuery.setSeller(seller);
        orderQuery.setBuyer(buyer);
        orderQuery.setStatus(status);
        orderQuery.setPaymentStatus(paymentStatus);
        orderQuery.setDateTimeFrom(dateFrom);
        orderQuery.setDateTimeTo(dateTo);
        SqlUtil sqlUtil = new SqlUtil();
        ObservableList<Order> orders = FXCollections.observableList(new ArrayList<>());
        try {
            sqlUtil.connect();
            String sql = SELECT_ALL + orderQuery.getQueryCondition();
            ResultSet rs = sqlUtil.exeQuery(sql);
            List<Order> orderList = orderMapper.mapToOrder(rs);
            orders = FXCollections.observableList(orderList);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return orders;
    }

    public Order getTopByLicensePlates(String licensePlates) {
        ObservableList<Order> orders = getOrderFilters(licensePlates, null, null, null, null, LocalDateTime.of(Year.now().getValue() - 2, 01, 01, 0, 0, 0), LocalDateTime.now());
        Order order = null;
        if (orders.size() > 0) {
            order = orders.get(0);
        }
        return order;
    }

    public Order getOrderOnByObservableListByIndex(int index) {
        return null;
    }

    public Order createOrder(Order order) {
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String query = String.format(INSERT_ORDER,
                    order.getIndex(), order.getLicensePlates(),
                    order.getSeller(), order.getBuyer(), order.getTotalWeight(),
                    order.getVehicleWeight(), order.getCargoWeight(), DateUtil.convertToString(LocalDateTime.now(), DateUtil.DB_FORMAT),
                    DateUtil.convertToString(LocalDateTime.now(), DateUtil.DB_FORMAT), order.getStatus(), order.getPaymentStatus(),
                    order.getCargoType(), order.getPaymentAmount(), order.getNote(),
                    order.getPayer(), order.getCreatedBy());
            sqlUtil.exeQuery(query);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return order;
    }

    public Order updateOrder(Order order) {
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String query = String.format(UPDATE_ORDER_BY_ID,
                    order.getLicensePlates(), order.getSeller(), order.getBuyer(),
                    order.getTotalWeight(), order.getVehicleWeight(), order.getCargoWeight(),
                    DateUtil.convertToString(LocalDateTime.now(), DateUtil.DB_FORMAT), order.getStatus(), order.getPaymentStatus(),
                    order.getCargoType(), order.getPaymentAmount(), order.getNote(), order.getPayer(),
                    order.getId());
            sqlUtil.exeQuery(query);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return order;
    }

    public void deleteOrder(Order order) {
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String query = String.format(UPDATE_ORDER_STATUS_BY_ID, CANCELED.getNote(), order.getId());
            sqlUtil.exeQuery(query);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
    }
}
