package com.example.demo.controller;

import com.example.demo.dao.OrderDAO;
import com.example.demo.service.IOService;
import com.example.demo.utils.constants.OrderStatus;
import com.example.demo.utils.constants.PaymentStatus;
import com.example.demo.utils.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class AdminHomeController implements Initializable {
    @FXML
    private BarChart orderBarChart;
    @FXML
    private LineChart orderLineChart;
    @FXML
    private ComboBox<Integer> startMonth;
    @FXML
    private ComboBox<Integer> startYear;
    @FXML
    private ComboBox<Integer> endMonth;
    @FXML
    private ComboBox<Integer> endYear;
    @FXML
    private Label totalAmountLabel;
    @FXML
    private Label totalPaidAmountLabel;
    @FXML
    private Label totalUnpaidAmountLabel;
    @FXML
    private Label totalCancelLabel;
    @FXML
    private Label totalOrderLabel;
    private OrderDAO orderDAO = OrderDAO.getInstance();
    private IOService ioService = new IOService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocalDate now = LocalDate.now();
        LocalDate yearAgo = now.minusMonths(6);
        ObservableList<Integer> months = FXCollections.observableList(new ArrayList<>());
        ObservableList<Integer> years = FXCollections.observableList(new ArrayList<>());
        for (Month month : Month.values()) {
            months.add(month.getValue());
        }
        for (int index = now.getYear(); index >= now.getYear() - 5; index--) {
            years.add(index);
        }
        startMonth.setItems(months);
        endMonth.setItems(months);
        startYear.setItems(years);
        endYear.setItems(years);
        startMonth.setValue(yearAgo.getMonthValue());
        startYear.setValue(yearAgo.getYear());
        endMonth.setValue(now.getMonthValue());
        endYear.setValue(now.getYear());
        filter(orderBarChart);
        filter(orderLineChart);
    }

    private void filter(XYChart xyChart) {
        xyChart.getData().removeAll(Collections.singleton(xyChart.getData().setAll()));
        XYChart.Series totalOrderData = new XYChart.Series();
        XYChart.Series totalAmountData = new XYChart.Series();
        XYChart.Series totalPaidAmountData = new XYChart.Series();
        XYChart.Series totalUnpaidAmountData = new XYChart.Series();
        XYChart.Series totalCancelAmountData = new XYChart.Series();
        LocalDate startDate = DateUtil.getFirstDayOfMonth(startMonth.getValue(), startYear.getValue());
        LocalDate endDate = DateUtil.getFirstDayOfMonth(endMonth.getValue(), endYear.getValue());
        totalAmountData.setName("Tổng doanh thu");
        totalOrderData.setName("Tổng số lượng cân");
        totalPaidAmountData.setName("Đã trả");
        totalUnpaidAmountData.setName("Chưa trả");
        totalCancelAmountData.setName("Hủy mã cân");
        long totalOrders = 0;
        long totalAmount = 0;
        long totalPaidAmount = 0;
        long totalUnpaidAmount = 0;
        long totalCancel = 0;
        while (!startDate.isAfter(endDate)) {
            LocalDate firstDayOfMonth = DateUtil.getFirstDayOfMonth(startDate);
            LocalDate lastDayOfMonth = DateUtil.getLastDayOfMonth(startDate);
            long monthTotal = orderDAO.countOrderBetweenDates(firstDayOfMonth, lastDayOfMonth, OrderStatus.ALL);
            long monthTotalPaidAmount = orderDAO.getSumPaymentBetweenDates(firstDayOfMonth, lastDayOfMonth, PaymentStatus.PAID);
            long monthCancel = orderDAO.countOrderBetweenDates(firstDayOfMonth, lastDayOfMonth, OrderStatus.CANCELED);
            long totalMonthAmount = orderDAO.getSumPaymentBetweenDates(firstDayOfMonth, lastDayOfMonth, PaymentStatus.ALL);
            long monthTotalUnpaidAmount = orderDAO.getSumPaymentBetweenDates(firstDayOfMonth, lastDayOfMonth, PaymentStatus.UNPAID);
            totalOrderData.getData().add(new XYChart.Data(String.valueOf(startDate.getMonthValue()), monthTotal));
            totalAmountData.getData().add(new XYChart.Data(String.valueOf(startDate.getMonthValue()), totalMonthAmount));
            totalPaidAmountData.getData().add(new XYChart.Data(String.valueOf(startDate.getMonthValue()), monthTotalPaidAmount));
            totalUnpaidAmountData.getData().add(new XYChart.Data(String.valueOf(startDate.getMonthValue()), monthTotalUnpaidAmount));
            totalCancelAmountData.getData().add(new XYChart.Data(String.valueOf(startDate.getMonthValue()), monthCancel));
            totalOrders += monthTotal;
            totalAmount += totalMonthAmount;
            totalPaidAmount += monthTotalPaidAmount;
            totalUnpaidAmount += monthTotalUnpaidAmount;
            totalCancel += monthCancel;
            startDate = startDate.plusMonths(1);
        }
        xyChart.getData().add(totalOrderData);
        xyChart.getData().add(totalAmountData);
        xyChart.getData().add(totalPaidAmountData);
        xyChart.getData().add(totalUnpaidAmountData);
        xyChart.getData().add(totalCancelAmountData);
        totalOrderLabel.setText(String.valueOf(totalOrders));
        totalAmountLabel.setText(String.valueOf(totalAmount));
        totalPaidAmountLabel.setText(String.valueOf(totalPaidAmount));
        totalUnpaidAmountLabel.setText(String.valueOf(totalUnpaidAmount));
        totalCancelLabel.setText(String.valueOf(totalCancel));
    }

    public void search() {
        LocalDate startDate = DateUtil.getFirstDayOfMonth(startMonth.getValue(), startYear.getValue());
        LocalDate endDate = DateUtil.getFirstDayOfMonth(endMonth.getValue(), endYear.getValue());
        if (startDate.isAfter(endDate)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Lọc thất bại");
            alert.setHeaderText(null);
            alert.setContentText("Ngày bắt đầu không thể lớn hơn ngày kết thúc");
            alert.show();
            return;
        }
        filter(orderLineChart);
        filter(orderBarChart);
    }

    public void importOrder(ActionEvent actionEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
        fileChooser.setTitle("Chọn file CSV");
        File file = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());
        boolean isSuccess = ioService.importData(file);
        Alert alert = null;
        String title = "Nhập dữ liệu dữ liệu";
        if (isSuccess) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(title + " thành công");
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(title + " thất bại");
        }
        alert.show();
    }

    public void export(ActionEvent actionEvent) throws FileNotFoundException {
        LocalDate startDate = DateUtil.getFirstDayOfMonth(startMonth.getValue(), startYear.getValue());
        LocalDate endDate = DateUtil.getFirstDayOfMonth(endMonth.getValue(), endYear.getValue());
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(((Node) actionEvent.getSource()).getScene().getWindow());
        if (selectedDirectory == null) {
            return;
        }
        boolean isSuccess = ioService.exportData(orderDAO.getOrderFilters(null,
                        null,
                        null,
                        null,
                        null,
                        startDate.atStartOfDay().withHour(0).withMinute(0).withSecond(0),
                        endDate.atStartOfDay().withHour(23).withMinute(59).withSecond(59)),
                selectedDirectory.getAbsolutePath());
        Alert alert = null;
        String title = "Xuất dữ liệu";
        if (isSuccess) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(title + " thành công");
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(title + " thất bại");
        }
        alert.show();
    }
}
