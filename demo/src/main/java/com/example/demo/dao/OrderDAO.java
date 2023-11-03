package com.example.demo.dao;

import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.Order;
import com.example.demo.query.OrderQuery;
import com.example.demo.utils.constants.OrderStatus;
import com.example.demo.utils.constants.PaymentStatus;
import com.example.demo.utils.util.DateUtil;
import com.example.demo.utils.util.SqlUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.lang.ObjectUtils;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.demo.utils.constants.OrderQueryConstants.*;
import static com.example.demo.utils.constants.OrderStatus.CANCELED;
import static com.example.demo.utils.constants.OrderStatus.CREATED;
import static com.example.demo.utils.util.ConvertUtil.replaceNullStringToBlank;

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

    public long countOrderBetweenDates(LocalDate startDate, LocalDate endDate, OrderStatus status) {
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.setDateTimeFrom(LocalDateTime.of(startDate, LocalTime.of(0, 0, 0)));
        orderQuery.setDateTimeTo(LocalDateTime.of(endDate, LocalTime.of(23, 59, 59)));
        orderQuery.setStatus(Objects.isNull(status) ? null : status.getNote());
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

    public long getSumPaymentBetweenDates(LocalDate startDate, LocalDate endDate, PaymentStatus paymentStatus) {
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.setDateTimeFrom(LocalDateTime.of(startDate, LocalTime.of(0, 0, 0)));
        orderQuery.setDateTimeTo(LocalDateTime.of(endDate, LocalTime.of(23, 59, 59)));
        orderQuery.setPaymentStatus(paymentStatus.getNote());
        orderQuery.setStatus(OrderStatus.ALL.getNote());
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String sql = GET_SUM_PAYMENT + orderQuery.getQueryCondition();
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
            String sql = GET_SUM_PAYMENT;
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

    public Order getById(String id) {
        SqlUtil sqlUtil = new SqlUtil();
        Order order = null;
        try {
            sqlUtil.connect();
            OrderQuery orderQuery = new OrderQuery();
            orderQuery.setId(id);
            String sql = SELECT_ALL + orderQuery.getQueryCondition();
            ResultSet rs = sqlUtil.exeQuery(sql);
            List<Order> orderList = orderMapper.mapToOrder(rs);
            List<Order> orders = FXCollections.observableList(orderList);
            if (orders.size() > 0) {
                order = orders.get(0);
            }
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return order;
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
                    order.getIndex(), replaceNullStringToBlank(order.getLicensePlates()),
                    replaceNullStringToBlank(order.getSeller()), replaceNullStringToBlank(order.getBuyer()), order.getTotalWeight(),
                    order.getVehicleWeight(), order.getCargoWeight(), DateUtil.convertToString(order.getCreatedAt(), DateUtil.DB_FORMAT),
                    DateUtil.convertToString(order.getUpdatedAt(), DateUtil.DB_FORMAT), replaceNullStringToBlank(order.getStatus()), replaceNullStringToBlank(order.getPaymentStatus()),
                    order.getCargoType(), order.getPaymentAmount(), order.getNote(),
                    replaceNullStringToBlank(order.getPayer()), replaceNullStringToBlank(order.getCreatedBy()));
            sqlUtil.exeUpdate(query);
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
                    replaceNullStringToBlank(order.getLicensePlates()), replaceNullStringToBlank(order.getSeller()), replaceNullStringToBlank(order.getBuyer()),
                    order.getTotalWeight(), order.getVehicleWeight(), order.getCargoWeight(),
                    DateUtil.convertToString(order.getUpdatedAt(), DateUtil.DB_FORMAT), replaceNullStringToBlank(order.getStatus()), replaceNullStringToBlank(order.getPaymentStatus()),
                    replaceNullStringToBlank(order.getCargoType()), order.getPaymentAmount(), replaceNullStringToBlank(order.getNote()), replaceNullStringToBlank(order.getPayer()),
                    order.getId());
            sqlUtil.exeUpdate(query);
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
            sqlUtil.exeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sqlUtil.disconnect();
        }
    }
}
