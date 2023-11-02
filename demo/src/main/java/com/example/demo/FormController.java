package com.example.demo;

import com.example.demo.dao.CargoDAO;
import com.example.demo.dao.HistoryLogDAO;
import com.example.demo.dao.OrderDAO;
import com.example.demo.data.CurrentUser;
import com.example.demo.model.Order;
import com.example.demo.service.ReportService;
import com.example.demo.utils.constants.OrderStatus;
import com.example.demo.utils.constants.PaymentStatus;
import com.example.demo.utils.util.ConvertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static com.example.demo.utils.constants.LogAction.UPDATED_ORDER_MANUAL;
import static com.example.demo.utils.constants.Page.FORM;
import static com.example.demo.utils.constants.PaymentStatus.PAID;
import static com.example.demo.utils.constants.PaymentStatus.UNPAID;
import static com.example.demo.utils.util.ConvertUtil.replaceNullStringToBlank;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.control.Alert.AlertType.WARNING;

public class FormController implements Initializable {
    @FXML
    private TextField indexTextField;
    @FXML
    private TextField licensePlatesTextField;
    @FXML
    private TextField sellerTextField;
    @FXML
    private TextField buyerTextField;
    @FXML
    private TextField noteTextField;
    @FXML
    private ComboBox<String> cargoComboBox;
    @FXML
    private TextField paymentAmountTextField;
    @FXML
    private TextField payerTextField;
    @FXML
    private TextField totalWeightTextField;
    @FXML
    private TextField vehicleWeightTextField;
    @FXML
    private TextField cargoWeightTextField;
    @FXML
    private RadioButton paidRadioButton;
    @FXML
    private RadioButton unpaidRadioButton;
    @FXML
    private Label id;
    private OrderDAO orderDAO = OrderDAO.getInstance();
    private HistoryLogDAO historyLogDAO = HistoryLogDAO.getInstance();
    private ReportService reportService = ReportService.getInstance();
    private CargoDAO cargoDAO = CargoDAO.getInstance();
    private Order selectedOrder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargoComboBox.setItems(cargoDAO.getAll());
    }

    public void setValue(Order order) {
        if (order == null) {
            return;
        }
        id.setText(String.valueOf(order.getId()));
        indexTextField.setText(String.valueOf(order.getIndex()));
        licensePlatesTextField.setText(order.getLicensePlates());
        sellerTextField.setText(order.getSeller());
        buyerTextField.setText(order.getBuyer());
        noteTextField.setText(order.getNote());
        paymentAmountTextField.setText(String.valueOf(order.getPaymentAmount()));
        payerTextField.setText(order.getPayer());
        totalWeightTextField.setText(String.valueOf(order.getTotalWeight()));
        vehicleWeightTextField.setText(String.valueOf(order.getVehicleWeight()));
        cargoWeightTextField.setText(String.valueOf(order.getCargoWeight()));
        cargoComboBox.setValue(order.getCargoType());
        PaymentStatus paymentStatus = PaymentStatus.getByNote(order.getPaymentStatus());
        if (PAID.equals(paymentStatus)) {
            paidRadioButton.setSelected(true);
            paidRadioButton.setDisable(true);
            unpaidRadioButton.setDisable(true);
            paymentAmountTextField.setDisable(true);
            payerTextField.setDisable(true);
        } else {
            unpaidRadioButton.setSelected(true);
        }
        selectedOrder = order;
        totalWeightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Integer parseValue = Integer.valueOf(newValue);
                totalWeightTextField.setText(parseValue.toString());
            } catch (IllegalArgumentException ex) {
                totalWeightTextField.setText(String.valueOf(selectedOrder.getTotalWeight()));
            }
        });
        vehicleWeightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Integer parseValue = Integer.valueOf(newValue);
                vehicleWeightTextField.setText(parseValue.toString());
            } catch (IllegalArgumentException ex) {
                vehicleWeightTextField.setText(String.valueOf(selectedOrder.getVehicleWeight()));
            }
        });
        paymentAmountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Integer parseValue = Integer.valueOf(newValue);
                paymentAmountTextField.setText(parseValue.toString());
            } catch (IllegalArgumentException ex) {
                paymentAmountTextField.setText(String.valueOf(selectedOrder.getPaymentAmount()));
            }
        });
    }

    public void updateOrder(ActionEvent actionEvent) {
        Alert alert;
        try {

            int firstValue = Integer.valueOf(totalWeightTextField.getText());
            int secondValue = Integer.valueOf(vehicleWeightTextField.getText());
            Order order = orderDAO.getById(id.getText());
            Order oldOrder = order.clone();
            if (firstValue >= secondValue) {
                order.setTotalWeight(firstValue);
                order.setVehicleWeight(secondValue);
            } else {
                order.setTotalWeight(secondValue);
                order.setVehicleWeight(firstValue);
            }
            String errorMessage = "";
            double paymentAmount = Double.valueOf(StringUtils.isBlank(paymentAmountTextField.getText()) ? "0" : paymentAmountTextField.getText());
            if (paymentAmount <= 0) {
                errorMessage = "Số tiền thanh toán phải lớn hơn 0";
            }
            if (StringUtils.isBlank(payerTextField.getText())) {
                errorMessage = "Số tiền thanh toán phải lớn hơn 0";
            }
            if (StringUtils.isNotBlank(errorMessage)) {
                alert = new Alert(WARNING);
                alert.setTitle("Lỗi cập nhật");
                alert.setHeaderText(null);
                alert.setContentText(errorMessage);
                alert.show();
                return;
            }
            if (paidRadioButton.isSelected() && !PAID.getNote().equals(order.getPaymentStatus())) {
                alert = new Alert(WARNING);
                alert.setTitle("Lưu ý cập nhật");
                alert.setHeaderText(null);
                alert.setContentText("Sau khi cập nhật bạn sẽ không thể thay đổi giá tiền, người bán và trạng thái thanh toán được nữa.\nBạn chắc chắn chứ?");
                alert.showAndWait();
            }
            int cargoValue = Math.abs(firstValue - secondValue);
            order.setCargoWeight(cargoValue);
            order.setLicensePlates(licensePlatesTextField.getText().toUpperCase());
            order.setSeller(replaceNullStringToBlank(sellerTextField.getText()));
            order.setBuyer(replaceNullStringToBlank(buyerTextField.getText()));
            order.setUpdatedAt(LocalDateTime.now());
            order.setCargoType(cargoComboBox.getValue());
            order.setPaymentAmount(Double.valueOf(StringUtils.isBlank(paymentAmountTextField.getText()) ? "0" : paymentAmountTextField.getText()));
            order.setNote(noteTextField.getText());
            order.setCreatedBy(CurrentUser.getInstance().getUsername());
            order.setPayer(payerTextField.getText());
            order.setStatus(OrderStatus.COMPLETED.getNote());
            order.setPaymentStatus(paidRadioButton.isSelected() ? PAID.getNote() : UNPAID.getNote());
            orderDAO.updateOrder(order);
            historyLogDAO.createLogForOrder(oldOrder, order, UPDATED_ORDER_MANUAL);
            resetData(order);
            alert = new Alert(INFORMATION);
            alert.setTitle("Cập nhật thành công");
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật mã cân " + order.getIndex() + " thành công!");
            alert.showAndWait();
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi cập nhật");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng kiểm tra lại giá trị cập nhật");
            alert.show();
        }
    }

    private void resetData(Order order) {
        selectedOrder = order;
        indexTextField.setText(String.valueOf(order.getIndex()));
        licensePlatesTextField.setText(order.getLicensePlates());
        sellerTextField.setText(order.getSeller());
        buyerTextField.setText(order.getBuyer());
        noteTextField.setText(order.getNote());
        cargoComboBox.setValue(order.getCargoType());
        payerTextField.setText(order.getPayer());
        totalWeightTextField.setText(String.valueOf(order.getTotalWeight()));
        vehicleWeightTextField.setText(String.valueOf(order.getVehicleWeight()));
        cargoWeightTextField.setText(String.valueOf(order.getCargoWeight()));
        paymentAmountTextField.setText(String.valueOf(order.getPaymentAmount()));
        if (PAID.getNote().equalsIgnoreCase(order.getPaymentStatus())) {
            paidRadioButton.setSelected(true);
            paidRadioButton.setDisable(true);
            unpaidRadioButton.setDisable(true);
            paymentAmountTextField.setDisable(true);
            payerTextField.setDisable(true);
        } else {
            unpaidRadioButton.setSelected(true);
        }
    }

    public void openOrderDetail(ActionEvent actionEvent) throws JRException {
        reportService.printOrderDetail(selectedOrder, false);
    }

    public void rollback() {
        resetData(selectedOrder);
    }
}
