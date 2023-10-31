package com.example.demo;

import com.example.demo.dao.VehicleDAO;
import com.example.demo.dao.WeightMoneyDAO;
import com.example.demo.model.Vehicle;
import com.example.demo.model.WeightMoney;
import com.example.demo.utils.constants.VehicleType;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang.StringUtils;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class WeighMoneyController implements Initializable {
    @FXML
    private TextField startWeightTextField;
    @FXML
    private TextField endWeightTextField;
    @FXML
    private TextField amountMoneyTextField;
    @FXML
    private TextField minAmountTextField;
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
    @FXML
    private TextField vehicleNameTextField;
    @FXML
    private TextField vehiclePatternTextField;
    @FXML
    private TableView<Vehicle> vehicleTable;
    @FXML
    private TableColumn<Vehicle, String> vehicleNameCol;
    @FXML
    private TableColumn<Vehicle, String> vehiclePatternCol;
    private ObservableList<WeightMoney> moneyObservableList;
    private WeightMoney selectedWeightMoney;
    private Vehicle selectedVehicle;
    private ObservableList<Vehicle> vehicles;
    private WeightMoneyDAO weightMoneyDAO = WeightMoneyDAO.getInstance();
    private VehicleDAO vehicleDAO = VehicleDAO.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vehicles = vehicleDAO.getAll();
        vehicleTable.setItems(vehicles);
        vehicleNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        vehiclePatternCol.setCellValueFactory(new PropertyValueFactory("pattern"));
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        startWeightCol.setCellValueFactory(new PropertyValueFactory("startWeight"));
        endWeightCol.setCellValueFactory(new PropertyValueFactory("endWeight"));
        amountMoneyCol.setCellValueFactory(new PropertyValueFactory("amountMoney"));
        typeCol.cellValueFactoryProperty().setValue(cellData -> {
            Vehicle vehicle = vehicles.parallelStream().filter(v -> v.getId() == cellData.getValue().getVehicleId()).findFirst().orElse(null);
            String name = "";
            if (!Objects.isNull(vehicle)) {
                name = vehicle.getName();
            }
            return new SimpleStringProperty(name);
        });
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
        minAmountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Integer parseValue = Integer.valueOf(newValue);
                minAmountTextField.setText(parseValue.toString());
            } catch (IllegalArgumentException ex) {
                minAmountTextField.setText("0");
            }
        });
        typeCombobox.setItems(FXCollections.observableList(vehicles.stream().map(Vehicle::getName).collect(Collectors.toList())));
        typeCombobox.setValue(VehicleType.CAR.name());
        weightMoneyTable.setPlaceholder(new Label(""));
        weightMoneyTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedWeightMoney = newValue;
            if (selectedWeightMoney != null) {
                startWeightTextField.setText(String.valueOf(selectedWeightMoney.getStartWeight()));
                endWeightTextField.setText(String.valueOf(selectedWeightMoney.getEndWeight()));
                amountMoneyTextField.setText(String.valueOf(selectedWeightMoney.getAmountMoney()));
                minAmountTextField.setText(String.valueOf(selectedWeightMoney.getMinAmount()));
                Vehicle vehicle = vehicles.parallelStream().filter(v -> v.getId() == selectedWeightMoney.getVehicleId()).findFirst().orElse(null);
                typeCombobox.setValue(vehicle.getName());
            }
        });
        vehicleTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedVehicle = newValue;
            if (selectedVehicle != null) {
                vehicleNameTextField.setText(selectedVehicle.getName());
                vehiclePatternTextField.setText(selectedVehicle.getPattern());
            }
        });
    }

    private boolean validateWeightMoney() {
        int startWeight = Integer.valueOf(startWeightTextField.getText());
        int endWeight = Integer.valueOf(endWeightTextField.getText());
        double amountMoney = Double.valueOf(amountMoneyTextField.getText());
        double minAmount = Double.valueOf(minAmountTextField.getText());
        String message = "";
        if (startWeight > endWeight) {
            message = "Khối lượng kết thúc không thể nhỏ hơn bắt đầu";
        }
        if (amountMoney == 0) {
            message = "Hãy nhập số tiền!";
        }
        if (minAmount > amountMoney) {
            message = "Số tiền nhỏ nhất không thể lớn hơn số tiền chính!";
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
        double minAmount = Double.valueOf(minAmountTextField.getText());
        if (!validateWeightMoney()) {
            return;
        }
        WeightMoney weightMoney = selectedWeightMoney;
        Vehicle vehicle = vehicles.parallelStream().filter(v -> v.getName().equalsIgnoreCase(typeCombobox.getValue())).findFirst().orElse(null);
        if (weightMoney == null) {
            weightMoney = new WeightMoney();
            weightMoney.setStartWeight(startWeight);
            weightMoney.setEndWeight(endWeight);
            weightMoney.setAmountMoney(amountMoney);
            weightMoney.setMinAmount(minAmount);
            weightMoney.setVehicleId(vehicle.getId());
            weightMoneyDAO.createWeightMoney(weightMoney);
        } else {
            weightMoney.setStartWeight(startWeight);
            weightMoney.setEndWeight(endWeight);
            weightMoney.setAmountMoney(amountMoney);
            weightMoney.setMinAmount(minAmount);
            weightMoney.setVehicleId(vehicle.getId());
            weightMoneyDAO.updateWeightMoney(weightMoney);
        }
        cleanData();
    }
    public void saveVehicle() {
        String name = vehicleNameTextField.getText();
        String pattern = vehiclePatternTextField.getText();
        if (StringUtils.isBlank(name) || StringUtils.isBlank(pattern)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Lưu phương tiện thất bại");
            alert.setHeaderText(null);
            alert.setContentText("Không được để trống trường");
            alert.show();
            return;
        }
        Vehicle vehicle = selectedVehicle;
        if (vehicle == null) {
            vehicle = new Vehicle();
            vehicle.setName(name);
            vehicle.setPattern(pattern);
            vehicleDAO.createVehicle(vehicle);
        } else {
            vehicle.setName(name);
            vehicle.setPattern(pattern);
            vehicleDAO.updateVehicle(vehicle);
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
        vehicles = vehicleDAO.getAll();
        vehicleTable.setItems(vehicleDAO.getAll());
        weightMoneyTable.getSelectionModel().clearSelection();
        startWeightTextField.setText("");
        endWeightTextField.setText("");
        amountMoneyTextField.setText("");
        vehicleNameTextField.setText("");
        vehiclePatternTextField.setText("");
        selectedWeightMoney = null;
        selectedVehicle = null;
    }
}
