package com.example.demo;

import com.example.demo.dao.WeightMoneyDAO;
import com.example.demo.model.WeightMoney;
import com.example.demo.utils.constants.VehicleType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang.StringUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class WeighMoneyController implements Initializable {
    @FXML
    private TextField startWeightTextField;
    @FXML
    private TextField endWeightTextField;
    @FXML
    private TextField amountMoneyTextField;
    @FXML
    private ComboBox<String> typeCombobox;
    @FXML
    private TableView<WeightMoney> weightMoneyTable;
    @FXML
    private TableColumn<WeightMoney, Integer> idCol;
    @FXML
    private TableColumn<WeightMoney, Integer> startWeightCol;
    @FXML
    private TableColumn<WeightMoney, Integer> endWeightCol;
    @FXML
    private TableColumn<WeightMoney, Double> amountMoneyCol;
    @FXML
    private TableColumn<WeightMoney, String> typeCol;
    private WeightMoneyDAO weightMoneyDAO = WeightMoneyDAO.getInstance();
    private ObservableList<WeightMoney> moneyObservableList;
    private WeightMoney selectedWeightMoney;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        startWeightCol.setCellValueFactory(new PropertyValueFactory("startWeight"));
        endWeightCol.setCellValueFactory(new PropertyValueFactory("endWeight"));
        amountMoneyCol.setCellValueFactory(new PropertyValueFactory("amountMoney"));
        typeCol.setCellValueFactory(new PropertyValueFactory("type"));
        moneyObservableList = weightMoneyDAO.getAll();
        weightMoneyTable.setItems(moneyObservableList);
        startWeightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Integer parseValue = Integer.valueOf(newValue);
                startWeightTextField.setText(parseValue.toString());
            } catch (IllegalArgumentException ex) {
                startWeightTextField.setText("0");
            }
        });
        endWeightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Integer parseValue = Integer.valueOf(newValue);
                endWeightTextField.setText(parseValue.toString());
            } catch (IllegalArgumentException ex) {
                endWeightTextField.setText("0");
            }
        });
        amountMoneyTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Integer parseValue = Integer.valueOf(newValue);
                amountMoneyTextField.setText(parseValue.toString());
            } catch (IllegalArgumentException ex) {
                amountMoneyTextField.setText("0");
            }
        });
        typeCombobox.setItems(FXCollections.observableList(VehicleType.getVehicleTypes()));
        typeCombobox.setValue(VehicleType.CAR.name());
        weightMoneyTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedWeightMoney = newValue;
            if (selectedWeightMoney != null) {
                startWeightTextField.setText(String.valueOf(selectedWeightMoney.getStartWeight()));
                endWeightTextField.setText(String.valueOf(selectedWeightMoney.getEndWeight()));
                amountMoneyTextField.setText(String.valueOf(selectedWeightMoney.getAmountMoney()));
                typeCombobox.setValue(selectedWeightMoney.getType());
            }
        });
    }

    private boolean validate() {
        int startWeight = Integer.valueOf(startWeightTextField.getText());
        int endWeight = Integer.valueOf(endWeightTextField.getText());
        double amountMoney = Double.valueOf(amountMoneyTextField.getText());
        String message = "";
        if (startWeight > endWeight) {
            message = "Khối lượng kết thúc không thể nhỏ hơn bắt đầu";
        }
        if (amountMoney == 0) {
            message = "Hãy nhập số tiền!";
        }
        if (StringUtils.isNotBlank(message)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Lưu tiền cân thất bại");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.show();
            return false;
        }
        return true;
    }

    public void saveWeightMoney() {
        int startWeight = Integer.valueOf(startWeightTextField.getText());
        int endWeight = Integer.valueOf(endWeightTextField.getText());
        double amountMoney = Double.valueOf(amountMoneyTextField.getText());
        if (!validate()) {
            return;
        }
        WeightMoney weightMoney = selectedWeightMoney;
        if (weightMoney == null) {
            weightMoney = new WeightMoney();
            weightMoney.setId(weightMoneyDAO.countWeightMoney()+1);
            weightMoney.setStartWeight(startWeight);
            weightMoney.setEndWeight(endWeight);
            weightMoney.setAmountMoney(amountMoney);
            weightMoney.setType(typeCombobox.getValue());
            weightMoneyDAO.createWeightMoney(weightMoney);
        } else {
            weightMoney.setStartWeight(startWeight);
            weightMoney.setEndWeight(endWeight);
            weightMoney.setAmountMoney(amountMoney);
            weightMoneyDAO.updateWeightMoney(weightMoney);
        }
        cleanData();
    }

    public void rollback() {
        cleanData();
        weightMoneyTable.getSelectionModel().clearSelection();
    }

    private void cleanData() {
        moneyObservableList = weightMoneyDAO.getAll();
        weightMoneyTable.setItems(moneyObservableList);
        weightMoneyTable.getSelectionModel().clearSelection();
        startWeightTextField.setText("");
        endWeightTextField.setText("");
        amountMoneyTextField.setText("");
        selectedWeightMoney = null;
    }
}
