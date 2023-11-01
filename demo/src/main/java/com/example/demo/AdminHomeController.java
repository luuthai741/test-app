package com.example.demo;

import com.example.demo.dao.OrderDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminHomeController implements Initializable {
    @FXML
    private BarChart orderBarChart;
    @FXML
    private LineChart orderLineChart;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    private OrderDAO orderDAO = OrderDAO.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocalDate now = LocalDate.now();
        LocalDate yearAgo = now.minusMonths(12);
        startDatePicker.setValue(yearAgo);
        endDatePicker.setValue(now);
        filter(yearAgo, now);
    }

    private void filter(LocalDate startDate, LocalDate endDate) {
        XYChart.Series totalOrder = new XYChart.Series();
        XYChart.Series totalAmount = new XYChart.Series();
        while (!startDate.isAfter(endDate)) {
            long monthTotal = orderDAO.countOrderBetweenDates(startDate, startDate);
            long mothAmount = orderDAO.getSumPaymentBetweenDates(startDate, startDate);
            totalOrder.getData().add(new XYChart.Data(String.valueOf(startDate.getMonthValue()), monthTotal));
            totalAmount.getData().add(new XYChart.Data(String.valueOf(startDate.getMonthValue()), mothAmount));
            startDate = startDate.plusMonths(1);
        }
        orderLineChart.getData().add(totalOrder);
        orderLineChart.getData().add(totalAmount);
        orderBarChart.getData().add(totalOrder);
        orderBarChart.getData().add(totalAmount);
    }
}
