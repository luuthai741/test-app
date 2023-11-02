package com.example.demo;

import com.example.demo.dao.OrderDAO;
import com.example.demo.service.IOService;
import com.example.demo.utils.constants.OrderStatus;
import com.example.demo.utils.constants.PaymentStatus;
import com.example.demo.utils.util.DateUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
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
        startDatePicker.setValue(yearAgo);
        endDatePicker.setValue(now);
        filter(yearAgo, now);
    }

    private void filter(LocalDate startDate, LocalDate endDate) {
        orderLineChart.getData().removeAll(Collections.singleton(orderLineChart.getData().setAll()));
        orderBarChart.getData().removeAll(Collections.singleton(orderBarChart.getData().setAll()));
        XYChart.Series totalOrderLineChart = new XYChart.Series();
        XYChart.Series totalAmountLineChart = new XYChart.Series();
        XYChart.Series totalPaidAmountLineChart = new XYChart.Series();
        XYChart.Series totalUnpaidAmountLineChart = new XYChart.Series();
        XYChart.Series totalCancelAmountLineChart = new XYChart.Series();
        XYChart.Series totalOrderBarChart = new XYChart.Series();
        XYChart.Series totalAmountBarChart = new XYChart.Series();
        XYChart.Series totalPaidAmountBarChart = new XYChart.Series();
        XYChart.Series totalUnpaidAmountBarChart = new XYChart.Series();
        XYChart.Series totalCancelAmountBarChart = new XYChart.Series();
        totalAmountLineChart.setName("Tổng doanh thu");
        totalOrderLineChart.setName("Tổng số lượng cân");
        totalPaidAmountLineChart.setName("Đã trả");
        totalUnpaidAmountLineChart.setName("Chưa trả");
        totalCancelAmountLineChart.setName("Hủy mã cân");

        totalAmountBarChart.setName("Tổng doanh thu");
        totalOrderBarChart.setName("Tổng số lượng cân");
        totalPaidAmountBarChart.setName("Đã trả");
        totalUnpaidAmountBarChart.setName("Chưa trả");
        totalCancelAmountBarChart.setName("Hủy mã cân");
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
            totalOrderLineChart.getData().add(new XYChart.Data(String.valueOf(startDate.getMonthValue()), monthTotal));
            totalAmountLineChart.getData().add(new XYChart.Data(String.valueOf(startDate.getMonthValue()), totalMonthAmount));
            totalPaidAmountLineChart.getData().add(new XYChart.Data(String.valueOf(startDate.getMonthValue()), monthTotalPaidAmount));
            totalUnpaidAmountLineChart.getData().add(new XYChart.Data(String.valueOf(startDate.getMonthValue()), monthTotalUnpaidAmount));
            totalCancelAmountLineChart.getData().add(new XYChart.Data(String.valueOf(startDate.getMonthValue()), monthCancel));
            totalOrderBarChart.getData().add(new XYChart.Data(String.valueOf(startDate.getMonthValue()), monthTotal));
            totalAmountBarChart.getData().add(new XYChart.Data(String.valueOf(startDate.getMonthValue()), totalMonthAmount));
            totalPaidAmountBarChart.getData().add(new XYChart.Data(String.valueOf(startDate.getMonthValue()), monthTotalPaidAmount));
            totalUnpaidAmountBarChart.getData().add(new XYChart.Data(String.valueOf(startDate.getMonthValue()), monthTotalUnpaidAmount));
            totalCancelAmountBarChart.getData().add(new XYChart.Data(String.valueOf(startDate.getMonthValue()), monthCancel));
            totalOrders += monthTotal;
            totalAmount += totalMonthAmount;
            totalPaidAmount += monthTotalPaidAmount;
            totalUnpaidAmount += monthTotalUnpaidAmount;
            totalCancel += monthCancel;
            startDate = startDate.plusMonths(1);
        }
        orderLineChart.getData().add(totalOrderLineChart);
        orderLineChart.getData().add(totalAmountLineChart);
        orderLineChart.getData().add(totalPaidAmountLineChart);
        orderLineChart.getData().add(totalUnpaidAmountLineChart);
        orderLineChart.getData().add(totalCancelAmountLineChart);
        orderBarChart.getData().add(totalOrderBarChart);
        orderBarChart.getData().add(totalAmountBarChart);
        orderBarChart.getData().add(totalPaidAmountBarChart);
        orderBarChart.getData().add(totalUnpaidAmountBarChart);
        orderBarChart.getData().add(totalCancelAmountBarChart);
        totalOrderLabel.setText(String.valueOf(totalOrders));
        totalAmountLabel.setText(String.valueOf(totalAmount));
        totalPaidAmountLabel.setText(String.valueOf(totalPaidAmount));
        totalUnpaidAmountLabel.setText(String.valueOf(totalUnpaidAmount));
        totalCancelLabel.setText(String.valueOf(totalCancel));
    }

    public void search() {
        if (startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Lọc thất bại");
            alert.setHeaderText(null);
            alert.setContentText("Ngày bắt đầu không thể lớn hơn ngày kết thúc");
            alert.show();
            return;
        }
        filter(startDatePicker.getValue(), endDatePicker.getValue());
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
                        startDatePicker.getValue().atStartOfDay().withHour(0).withMinute(0).withSecond(0),
                        endDatePicker.getValue().atStartOfDay().withHour(23).withMinute(59).withSecond(59)),
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
