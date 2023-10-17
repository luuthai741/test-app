package com.example.demo;

import com.example.demo.model.Order;
import com.example.demo.utils.constants.PaymentStatus;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.util.List;

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
    private ComboBox cargoComboBox;
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

    public void setValue(Order order) {
        if (order == null) {
            return;
        }
        cargoComboBox.setItems(FXCollections.observableList(List.of("Sắt", "Than", "Dây 27")));
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
}
