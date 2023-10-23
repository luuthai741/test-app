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
import java.util.List;

import static com.example.demo.utils.constants.OrderQueryConstants.*;
import static com.example.demo.utils.constants.OrderStatus.CREATED;

public class OrderDAO {
    private static OrderDAO instance;

    private OrderDAO() {
    }

    private ObservableList<Order> orders;
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

    public Order getOrderOnByObservableListByIndex(int index) {
        return orders.stream().filter(order -> order.getIndex() == index).findFirst().orElse(null);
    }

    public Order createOrder(Order order) {
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String query = String.format(INSERT_ORDER,
                    order.getId(), order.getIndex(), order.getLicensePlates(),
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
}
