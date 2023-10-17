package com.example.demo;

import com.example.demo.dao.OrderDAO;
import com.example.demo.model.Order;
import com.example.demo.utils.constants.OrderStatus;
import com.example.demo.utils.constants.Page;
import com.example.demo.utils.constants.PaymentStatus;
import com.example.demo.utils.util.ConvertUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.example.demo.utils.constants.Page.FORM;
import static com.example.demo.utils.util.ConvertUtil.replaceNullStringToBlank;

public class FormController {
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
    private OrderDAO orderDAO = OrderDAO.getInstance();

    public void setValue(Order order) {
        if (order == null) {
            return;
        }
//        cargoComboBox.setItems(FXCollections.observableList(List.of("Sắt", "Than", "Dây 27")));
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
        PaymentStatus paymentStatus = PaymentStatus.getByNote(order.getPaymentStatus());
        if (PaymentStatus.PAID.equals(paymentStatus)) {
            paidRadioButton.setSelected(true);
        } else {
            unpaidRadioButton.setSelected(true);
        }
    }

    public void updateOrder(ActionEvent actionEvent) {
        Alert alert;
        try {
            int firstValue = Integer.valueOf(totalWeightTextField.getText());
            int secondValue = Integer.valueOf(vehicleWeightTextField.getText());
            Order order = orderDAO.getOrderByIndex(Integer.valueOf(indexTextField.getText()));
            if (firstValue >= secondValue) {
                order.setTotalWeight(firstValue);
                order.setVehicleWeight(secondValue);
            } else {
                order.setTotalWeight(secondValue);
                order.setVehicleWeight(firstValue);
            }
            int cargoValue = Math.abs(firstValue - secondValue);
            order.setCargoWeight(cargoValue);
            order.setLicensePlates(licensePlatesTextField.getText());
            order.setSeller(replaceNullStringToBlank(sellerTextField.getText()));
            order.setBuyer(replaceNullStringToBlank(buyerTextField.getText()));
            order.setUpdatedAt(LocalDate.now());
            order.setStatus(OrderStatus.COMPLETED.name());
            order.setPaymentStatus(PaymentStatus.PAID.name());
            order.setCargoType(cargoComboBox.getValue());
            order.setPaymentAmount(Double.valueOf(paymentAmountTextField.getText().isBlank() ? "0" : paymentAmountTextField.getText()));
            order.setNote(noteTextField.getText());
            order.setCreatedBy("test");
            order.setPayer(payerTextField.getText());
            order.setPaymentStatus(paidRadioButton.isSelected() ? PaymentStatus.PAID.getNote() : PaymentStatus.UNPAID.getNote());
            orderDAO.updateOrder(order);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cập nhật thành công");
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật mã cân " + order.getIndex() + " thành công!");
            alert.showAndWait();
            reloadPopup(order, actionEvent);
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi cập nhật");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng kiểm tra lại giá trị cập nhật");
            alert.show();
        }
    }

    private void reloadPopup(Order order, ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FORM.getFxml()));
        Parent root = fxmlLoader.load();
        FormController formController = fxmlLoader.getController();
        formController.setValue(order);
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Chỉnh sửa mã cân");
        stage.show();
        ConvertUtil.PAGES.add(FORM.name());
        stage.setOnCloseRequest(e -> {
            ConvertUtil.PAGES.remove(FORM.name());
        });
    }

    public void openOrderDetail(ActionEvent actionEvent) throws IOException {
        Order order = orderDAO.getOrderByIndex(Integer.valueOf(indexTextField.getText()));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Page.ORDER.getFxml()));
        Parent root = fxmlLoader.load();
        OrderController orderController = fxmlLoader.getController();
        orderController.setValue(order);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
