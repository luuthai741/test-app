package com.example.demo.service;

import com.example.demo.data.OrderDTO;
import com.example.demo.model.Order;
import com.example.demo.utils.util.DateUtil;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.List;
import java.util.stream.Collectors;

public class ReportService {
    private static ReportService instance;

    private ReportService() {
    }

    public static ReportService getInstance() {
        if (instance == null) {
            instance = new ReportService();
        }
        return instance;
    }

    private final String ORDERS_PATH = "/jasper/orders.jasper";
    private final String ORDER_DETAIL_PATH = "jasper/order_detail.jasper";

    public JasperPrint printOrders(ObservableList<Order> orders) throws JRException {
//        List<OrderDTO> reportData = orders.stream().map(order -> {
//            OrderDTO orderDTO = new OrderDTO();
//            orderDTO.setIndex(order.getIndex());
//            orderDTO.setLicensesPlates(order.getLicensePlates());
//            orderDTO.setSeller(order.getSeller());
//            orderDTO.setBuyer(order.getSeller());
//            orderDTO.setTotalWeight(order.getTotalWeight());
//            orderDTO.setVehicleWeight(order.getVehicleWeight());
//            orderDTO.setCargoWeight(order.getCargoWeight());
//            orderDTO.setCreatedAt(DateUtil.convertToString(order.getCreatedAt(),DateUtil.DD_MM_YYYY));
//            orderDTO.setPaymentAmount(order.getPaymentAmount());
//            return orderDTO;
//        }).collect(Collectors.toList());
//        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(reportData);
//        getClass().getResource(ORDERS_PATH);
//        JasperReport report = null;
//        return JasperFillManager.fillReport(ORDERS_PATH, null, jrBeanCollectionDataSource);
        return null;
    }
}
