package com.example.demo;

import com.example.demo.dao.OrderDAO;
import com.example.demo.model.Order;
import com.example.demo.utils.constants.OrderStatus;
import com.example.demo.utils.constants.PaymentStatus;
import com.example.demo.utils.util.ConvertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.example.demo.utils.util.ConvertUtil.replaceNullStringToBlank;

public class IndexController implements Initializable {
    //BẢNG TRỌNG LƯỢNG
    @FXML
    private CheckBox manualCheckbox;
    @FXML
    private TextField manualTextField;
    @FXML
    private Label weightLabel;

    //BẢNG THÔNG TIN KHÁCH HÀNG
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
    private AnchorPane paymentPane;
    @FXML
    private TextField paymentAmountTextField;
    @FXML
    private ComboBox<String> paymentStatusComboBox;
    @FXML
    private TextField payerTextField;
    @FXML
    private CheckBox payerCheckBox;

    // THÔNG TIN SỐ CAN
    @FXML
    private AnchorPane weightDetailPane;
    @FXML
    private Label totalWeightLabel;
    @FXML
    private Label vehicleWeightLabel;
    @FXML
    private Label cargoWeightLabel;

    // THỰC THI
    @FXML
    private Button firstTimeButton;
    @FXML
    private Button secondTimeButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button printButton;
    @FXML
    private Button deleteButton;
    // DANH SÁCH
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, Integer> indexCol;
    @FXML
    private TableColumn<Order, Integer> licensePlatesCol;
    @FXML
    private TableColumn<Order, String> sellerCol;
    @FXML
    private TableColumn<Order, String> buyerCol;
    @FXML
    private TableColumn<Order, Integer> totalWeightCol;
    @FXML
    private TableColumn<Order, Integer> vehicleWeightCol;
    @FXML
    private TableColumn<Order, Integer> cargoWeightCol;
    @FXML
    private TableColumn<Order, String> cargoCol;
    @FXML
    private TableColumn<Order, LocalDate> createdAtCol;
    @FXML
    private Order selectedOrder = null;
    private OrderDAO orderDAO = OrderDAO.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Order> orders = orderDAO.getOrders();
        indexCol.setCellValueFactory(new PropertyValueFactory("index"));
        licensePlatesCol.setCellValueFactory(new PropertyValueFactory("licensePlates"));
        sellerCol.setCellValueFactory(new PropertyValueFactory("seller"));
        buyerCol.setCellValueFactory(new PropertyValueFactory("buyer"));
        totalWeightCol.setCellValueFactory(new PropertyValueFactory("totalWeight"));
        vehicleWeightCol.setCellValueFactory(new PropertyValueFactory("vehicleWeight"));
        cargoWeightCol.setCellValueFactory(new PropertyValueFactory("cargoWeight"));
        cargoCol.setCellValueFactory(new PropertyValueFactory("cargoType"));
        createdAtCol.setCellValueFactory(new PropertyValueFactory("createdAt"));
        orderTable.setItems(orders);
        orderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            firstTimeButton.setDisable(false);
            cleanData();
            selectedOrder = newValue;
            if (selectedOrder != null) {
                deleteButton.setDisable(false);
                printButton.setDisable(false);
                setValue(selectedOrder);
            }
        });
        manualTextField.setVisible(false);
        indexTextField.setEditable(false);

//        cargoComboBox.setItems(FXCollections.observableList(List.of("Sắt","Than","Dây 27")));

        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        statusComboBox.setItems(OrderStatus.getIndexStatus());
        statusComboBox.setValue(OrderStatus.CREATED.getNote());
        paymentStatusComboBox.setItems(PaymentStatus.getIndexStatus());
        paymentStatusComboBox.setValue(PaymentStatus.PAID.getNote());
        manualTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Long longValue = Long.valueOf(newValue);
                manualTextField.setText(newValue);
                weightLabel.setText(longValue.toString());
            } catch (IllegalArgumentException e) {
                manualTextField.setText("");
                weightLabel.setText("");
            }
        });
        weightDetailPane.setVisible(false);
        cleanData();
    }

    public void switchManual() {
        if (manualCheckbox.isSelected()) {
            manualTextField.setVisible(true);
        } else {
            manualTextField.setVisible(false);
            manualTextField.setText("0");
        }
    }

    public void fastChoicePayer() {
        if (payerCheckBox.isSelected()) {
            payerTextField.setText(sellerTextField.getText());
        } else {
            payerTextField.setText("");
        }
    }
    public void rollback() {
        cleanData();
    }

    public void actionFirstTime(ActionEvent actionEvent) {
        cleanData();
        int value = Integer.valueOf(weightLabel.getText());
        if (value == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Lỗi khi thực thi cân");
            alert.setHeaderText(null);
            alert.setContentText("Trọng lượng hàng không được bằng 0");
            alert.show();
            return;
        }
        int index = orderDAO.getOrders().size();
        indexTextField.setText(String.valueOf(index + 1));
        firstTimeButton.setDisable(true);
        weightDetailPane.setVisible(true);
        totalWeightLabel.setText(weightLabel.getText());
        saveButton.setDisable(false);
    }

    public void actionSecondTime(ActionEvent actionEvent) {
        Integer value = Integer.valueOf(weightLabel.getText());
        if (selectedOrder == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Lỗi khi thực thi cân");
            alert.setHeaderText(null);
            alert.setContentText("Hãy chọn mã cân");
            alert.show();
            return;
        }
        firstTimeButton.setDisable(true);
        secondTimeButton.setDisable(true);
        Integer totalWeight = selectedOrder.getTotalWeight();
        Integer cargoWeight = Math.abs(value - totalWeight);
        if (value >= totalWeight) {
            totalWeightLabel.setText(value.toString());
            vehicleWeightLabel.setText(totalWeight.toString());
        } else {
            totalWeightLabel.setText(totalWeight.toString());
            vehicleWeightLabel.setText(value.toString());
        }
        cargoWeightLabel.setText(cargoWeight.toString());
        saveButton.setDisable(false);
        paymentPane.setVisible(true);
    }

    public void saveOrder(ActionEvent actionEvent) {
        String errorText = "";
        if (licensePlatesTextField.getText().isBlank()) {
            errorText = "Biển số xe không được để trống!";
        } else if (weightLabel.getText().isBlank() || weightLabel.getText().equals("0")) {
            errorText = "Trọng lượng hàng không được bằng 0";
        }
        if (!errorText.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Lỗi tạo mã cân");
            alert.setHeaderText(null);
            alert.setContentText(errorText);
            alert.show();
            return;
        }
        Order order = null;
        if (selectedOrder == null) {
            order = new Order();
            order.setId(Integer.valueOf(indexTextField.getText()));
            order.setIndex(Integer.valueOf(indexTextField.getText()));
            order.setLicensePlates(licensePlatesTextField.getText());
            order.setSeller(replaceNullStringToBlank(sellerTextField.getText()));
            order.setBuyer(replaceNullStringToBlank(buyerTextField.getText()));
            order.setTotalWeight(Integer.valueOf(totalWeightLabel.getText()));
            order.setVehicleWeight(0);
            order.setCargoWeight(0);
            order.setCreatedAt(LocalDate.now());
            order.setUpdatedAt(LocalDate.now());
            order.setStatus(OrderStatus.CREATED.name());
            order.setPaymentStatus(PaymentStatus.UNPAID.name());
            order.setCargoType(cargoComboBox.getValue());
            order.setPaymentAmount(0);
            order.setNote(noteTextField.getText());
            order.setCreatedBy("test");
            orderDAO.createOrder(order);
        } else {
            selectedOrder.setIndex(Integer.valueOf(indexTextField.getText()));
            selectedOrder.setLicensePlates(licensePlatesTextField.getText());
            selectedOrder.setSeller(replaceNullStringToBlank(sellerTextField.getText()));
            selectedOrder.setBuyer(replaceNullStringToBlank(buyerTextField.getText()));
            selectedOrder.setTotalWeight(Integer.valueOf(totalWeightLabel.getText()));
            selectedOrder.setVehicleWeight(Integer.valueOf(vehicleWeightLabel.getText()));
            selectedOrder.setCargoWeight(Integer.valueOf(cargoWeightLabel.getText()));
            selectedOrder.setUpdatedAt(LocalDate.now());
            selectedOrder.setStatus(OrderStatus.COMPLETED.name());
            selectedOrder.setPaymentStatus(PaymentStatus.PAID.name());
            selectedOrder.setCargoType(cargoComboBox.getValue());
            selectedOrder.setPaymentAmount(paymentAmountTextField.getText().isBlank() ? 0 :Double.valueOf(paymentAmountTextField.getText()));
            selectedOrder.setNote(noteTextField.getText());
            selectedOrder.setPaymentStatus(paymentStatusComboBox.getValue());
            selectedOrder.setPayer(payerTextField.getText());
            selectedOrder.setCreatedBy("test");
            orderDAO.updateOrder(selectedOrder);
        }
        cleanData();
        firstTimeButton.setDisable(false);
        secondTimeButton.setDisable(false);
        weightDetailPane.setVisible(false);
    }

    private void cleanData() {
        indexTextField.setText("");
        licensePlatesTextField.setText("");
        sellerTextField.setText("");
        buyerTextField.setText("");
        noteTextField.setText("");
        paymentAmountTextField.setText("");
        totalWeightLabel.setText("");
        vehicleWeightLabel.setText("");
        cargoWeightLabel.setText("");
        selectedOrder = null;
        firstTimeButton.setDisable(false);
        secondTimeButton.setDisable(false);
        saveButton.setDisable(true);
        printButton.setDisable(true);
        deleteButton.setDisable(true);
        paymentPane.setVisible(false);
    }

    private void setValue(Order order) {
        indexTextField.setText(String.valueOf(order.getIndex()));
        licensePlatesTextField.setText(order.getLicensePlates());
        sellerTextField.setText(order.getSeller());
        buyerTextField.setText(order.getBuyer());
        noteTextField.setText(order.getNote());
        weightDetailPane.setVisible(true);
        totalWeightLabel.setText(String.valueOf(order.getTotalWeight()));
        vehicleWeightLabel.setText(String.valueOf(order.getVehicleWeight()));
        cargoWeightLabel.setText(String.valueOf(order.getCargoWeight()));
    }

    public void print(ActionEvent actionEvent){
        Label labelTen = new Label("Tên");
        TextField textFieldTen = new TextField();
        Label labelSoDienThoai = new Label("Số điện thoại");
        TextField textFieldSoDienThoai = new TextField();
        Label labelDiaChi = new Label("Địa chỉ");
        TextField textFieldDiaChi = new TextField();

        // Tạo một danh sách chứa các Node của các trường dữ liệu
        VBox vbox = new VBox();
        vbox.getChildren().addAll(labelTen, textFieldTen, labelSoDienThoai, textFieldSoDienThoai, labelDiaChi, textFieldDiaChi);

        // Tạo một nút "In"
        Button buttonIn = new Button("In");

        // Thêm nút "In" vào giao diện
        vbox.getChildren().add(buttonIn);
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        printerJob.printPage(vbox);
    }
}
