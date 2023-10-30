package com.example.demo.service;

import com.example.demo.HelloApplication;
import com.example.demo.dao.SettingDAO;
import com.example.demo.data.OrderDTO;
import com.example.demo.model.Order;
import com.example.demo.model.Setting;
import com.example.demo.utils.util.DateUtil;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.time.LocalDate;
import java.util.*;

import static com.example.demo.utils.constants.SettingKey.*;

public class ReportService {
    private static ReportService instance;
    private SettingDAO settingDAO = SettingDAO.getInstance();

    private ReportService() {
    }

    public static ReportService getInstance() {
        if (instance == null) {
            instance = new ReportService();
        }
        return instance;
    }

    private final String ORDERS_PATH = "jasper/orders.jrxml";
    private final String ORDER_DETAIL_PATH = "jasper/order_detail.jrxml";

    public void printOrders(ObservableList<Order> orders, LocalDate startDate, LocalDate endDate) throws JRException {
        List<OrderDTO> reportData = new ArrayList<>();
        int totalCargoWeight = 0;
        int totalPaymentAmount = 0;

        for (Order order : orders) {
            OrderDTO orderDTO = mapToOrderReport(order);
            reportData.add(orderDTO);
            totalCargoWeight += order.getCargoWeight();
            totalPaymentAmount += order.getPaymentAmount();
        }
        Map<String, Object> params = getGeneralParams();
        params.put("startDate", DateUtil.convertToString(startDate, DateUtil.DD_MM_YYYY));
        params.put("endDate", DateUtil.convertToString(endDate, DateUtil.DD_MM_YYYY));
        params.put("totalCargoWeight", String.valueOf(totalCargoWeight));
        params.put("totalPaymentAmount", String.valueOf(totalPaymentAmount));
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(reportData);
        JasperReport report = JasperCompileManager.compileReport(HelloApplication.class.getResourceAsStream(ORDERS_PATH));
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, jrBeanCollectionDataSource);

        JasperViewer.viewReport(jasperPrint, false);
    }

    public void printOrderDetail(Order order, boolean isFastPrint) throws JRException {
        OrderDTO orderDTO = mapToOrderReport(order);
        Map<String, Object> params = getGeneralParams();
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(List.of(orderDTO));
        JasperReport report = JasperCompileManager.compileReport(HelloApplication.class.getResourceAsStream(ORDER_DETAIL_PATH));
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, jrBeanCollectionDataSource);
        if (isFastPrint) {
            JasperPrintManager.printPage(jasperPrint, 0, true);
        } else {
            JasperViewer.viewReport(jasperPrint, false);
        }
    }

    private Map<String, Object> getGeneralParams() {
        Setting companyNameSetting = settingDAO.getByKey(COMPANY_NAME);
        Setting phoneSetting = settingDAO.getByKey(PHONE);
        Setting addressSetting = settingDAO.getByKey(ADDRESS);
        Map<String, Object> params = new HashMap<>();
        params.put("companyName", Objects.isNull(companyNameSetting) ? "" : companyNameSetting.getValue());
        params.put("mobile", Objects.isNull(phoneSetting) ? "" : phoneSetting.getValue());
        params.put("address", Objects.isNull(addressSetting) ? "" : addressSetting.getValue());
        return params;
    }

    private OrderDTO mapToOrderReport(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setIndex(order.getIndex());
        orderDTO.setLicensesPlates(order.getLicensePlates());
        orderDTO.setSeller(order.getSeller());
        orderDTO.setBuyer(order.getBuyer());
        orderDTO.setTotalWeight(order.getTotalWeight());
        orderDTO.setVehicleWeight(order.getVehicleWeight());
        orderDTO.setCargoWeight(order.getCargoWeight());
        orderDTO.setCreatedAt(DateUtil.convertToString(order.getCreatedAt(), DateUtil.DD_MM_YYYY));
        orderDTO.setUpdatedAt(DateUtil.convertToString(order.getUpdatedAt(), DateUtil.DD_MM_YYYY));
        orderDTO.setCreatedTime(DateUtil.convertToString(order.getCreatedAt(), DateUtil.HH_MM_SS));
        orderDTO.setUpdatedTime(DateUtil.convertToString(order.getUpdatedAt(), DateUtil.HH_MM_SS));
        orderDTO.setNote(order.getNote());
        orderDTO.setCargoType(order.getCargoType());
        orderDTO.setPaymentAmount(order.getPaymentAmount());
        orderDTO.setCreatedBy(order.getCreatedBy());
        return orderDTO;
    }
}
