package com.example.demo;

import com.example.demo.dao.OrderDAO;
import com.example.demo.model.Order;
import com.example.demo.service.PrintService;
import com.example.demo.utils.constants.OrderStatus;
import com.example.demo.utils.constants.Page;
import com.example.demo.utils.constants.PaymentStatus;
import com.example.demo.utils.util.DateUtil;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static com.example.demo.utils.util.ConvertUtil.replaceNullStringToBlank;
import static com.example.demo.utils.util.DateUtil.DD_MM_YYYY_HH_MM_SS;

public class IndexController implements Initializable {
    //BẢNG TRỌNG LƯỢNG
    @FXML
    private CheckBox manualCheckbox;
    @FXML
    private TextField manualTextField;
    @FXML
    private Label weightLabel;
    @FXML
    private Label currentTimeLabel;

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
    private RadioButton paidRadioButton;
    @FXML
    private RadioButton unpaidRadioButton;
    @FXML
    private TextField payerTextField;
    @FXML
    private CheckBox payerIsSellerCheckbox;
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
    private TableColumn<Order, Double> totalWeightCol;
    @FXML
    private TableColumn<Order, Double> vehicleWeightCol;
    @FXML
    private TableColumn<Order, Double> cargoWeightCol;
    @FXML
    private TableColumn<Order, String> cargoCol;
    @FXML
    private TableColumn<Order, LocalDate> createdAtCol;
    @FXML
    private Order selectedOrder = null;
    private OrderDAO orderDAO = OrderDAO.getInstance();
    private PrintService printService = PrintService.getInstance();
    private ObservableList<Order> orders;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orders = orderDAO.getOrders();
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
                printButton.setDisable(false);
                deleteButton.setDisable(false);
                setValue(selectedOrder);
            } else {
                printButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });
        manualTextField.setVisible(false);
        paymentPane.setVisible(false);
//        cargoComboBox.setItems(FXCollections.observableList(List.of("Sắt","Than","Dây 27")));

        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        statusComboBox.setItems(OrderStatus.getIndexStatus());
        statusComboBox.setValue(OrderStatus.CREATED.getNote());
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        manualTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            pause.setOnFinished(e ->{
                try {
                    Long longValue = Long.valueOf(newValue);
                    manualTextField.setText(newValue);
                    weightLabel.setText(longValue.toString());
                } catch (IllegalArgumentException ex) {
                    manualTextField.setText("");
                    weightLabel.setText("");
                }
            });
            pause.playFromStart();
        });
        weightDetailPane.setVisible(false);
        printButton.setDisable(true);
        deleteButton.setDisable(true);
        Thread timerThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000); //1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    currentTimeLabel.setText(DateUtil.convertToString(LocalDateTime.now(), DD_MM_YYYY_HH_MM_SS));
                });
            }
        });
        timerThread.start();
    }

    public void switchManual() {
        if (manualCheckbox.isSelected()) {
            manualTextField.setVisible(true);
        } else {
            manualTextField.setVisible(false);
            manualTextField.setText("0");
        }
    }

    public void rollback() {
        if (selectedOrder != null && (selectedOrder.getCargoWeight() != 0 || (selectedOrder.getTotalWeight() != 0 && selectedOrder.getVehicleWeight() != 0))) {
            orderDAO.getOrders().remove(selectedOrder);
        }
        orderTable.getSelectionModel().clearSelection();
        cleanData();
        paymentPane.setVisible(false);
        firstTimeButton.setDisable(false);
        secondTimeButton.setDisable(false);
        weightDetailPane.setVisible(false);
        orders = orderDAO.getOrders();
        orderTable.setItems(orders);
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
        long index = orderDAO.countOrderByDate(LocalDateTime.now());
        indexTextField.setText(String.valueOf(index + 1));
        secondTimeButton.setDisable(true);
        weightDetailPane.setVisible(true);
        totalWeightLabel.setText(weightLabel.getText());
    }

    public void actionSecondTime(ActionEvent actionEvent) {
        Double value = Double.valueOf(weightLabel.getText());
        if (selectedOrder == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Lỗi khi thực thi cân");
            alert.setHeaderText(null);
            alert.setContentText("Hãy chọn mã cân");
            alert.show();
            return;
        }
        firstTimeButton.setDisable(true);
        Integer totalWeight = selectedOrder.getTotalWeight();
        Double cargoWeight = Math.abs(value - totalWeight);
        if (value >= totalWeight) {
            totalWeightLabel.setText(String.valueOf(value.intValue()));
            vehicleWeightLabel.setText(String.valueOf(totalWeight.intValue()));
        } else {
            totalWeightLabel.setText(String.valueOf(totalWeight.intValue()));
            vehicleWeightLabel.setText(String.valueOf(value.intValue()));
        }
        cargoWeightLabel.setText(String.valueOf(cargoWeight.intValue()));
        paymentPane.setVisible(true);
        paymentAmountTextField.setEditable(true);
    }

    public void saveOrder(ActionEvent actionEvent) {
        String errorText = "";
        if (licensePlatesTextField.getText().isBlank()) {
            errorText = "Biển số xe không được để trống!";
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
            long id = orderDAO.countById() + 1;
            order.setId(id);
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
            firstTimeButton.setDisable(false);
            secondTimeButton.setDisable(false);
        } else {
            selectedOrder.setLicensePlates(licensePlatesTextField.getText());
            selectedOrder.setSeller(replaceNullStringToBlank(sellerTextField.getText()));
            selectedOrder.setBuyer(replaceNullStringToBlank(buyerTextField.getText()));
            selectedOrder.setTotalWeight(Double.valueOf(totalWeightLabel.getText()).intValue());
            selectedOrder.setVehicleWeight(Double.valueOf(vehicleWeightLabel.getText()).intValue());
            selectedOrder.setCargoWeight(Double.valueOf(cargoWeightLabel.getText()).intValue());
            selectedOrder.setUpdatedAt(LocalDate.now());
            selectedOrder.setStatus(OrderStatus.COMPLETED.name());
            selectedOrder.setPaymentStatus(PaymentStatus.PAID.name());
            selectedOrder.setCargoType(cargoComboBox.getValue());
            selectedOrder.setPaymentAmount(Double.valueOf(paymentAmountTextField.getText().isBlank() ? "0" : paymentAmountTextField.getText()));
            selectedOrder.setNote(noteTextField.getText());
            selectedOrder.setCreatedBy("test");
            selectedOrder.setPayer(payerTextField.getText());
            selectedOrder.setPaymentStatus(paidRadioButton.isSelected() ? PaymentStatus.PAID.getNote() : PaymentStatus.UNPAID.getNote());
            printButton.setDisable(false);
            orderDAO.updateOrder(selectedOrder);
            firstTimeButton.setDisable(true);
            secondTimeButton.setDisable(true);
            orderTable.setEditable(false);
        }

    }

    private void cleanData() {
        orderTable.setEditable(true);
        indexTextField.setText("");
        licensePlatesTextField.setText("");
        sellerTextField.setText("");
        buyerTextField.setText("");
        noteTextField.setText("");
        paymentAmountTextField.setText("");
        paymentAmountTextField.setEditable(false);
        totalWeightLabel.setText("");
        vehicleWeightLabel.setText("");
        cargoWeightLabel.setText("");
        selectedOrder = null;
        payerIsSellerCheckbox.setSelected(false);
        paidRadioButton.setSelected(false);
        unpaidRadioButton.setSelected(false);
    }

    private void setValue(Order order) {
        indexTextField.setText(String.valueOf(order.getIndex()));
        licensePlatesTextField.setText(order.getLicensePlates());
        sellerTextField.setText(order.getSeller());
        buyerTextField.setText(order.getBuyer());
        noteTextField.setText(order.getNote());
        weightDetailPane.setVisible(true);
        paymentAmountTextField.setText(String.valueOf(order.getPaymentAmount()));
        totalWeightLabel.setText(String.valueOf(order.getTotalWeight()));
        vehicleWeightLabel.setText(String.valueOf(order.getVehicleWeight()));
        cargoWeightLabel.setText(String.valueOf(order.getCargoWeight()));
        PaymentStatus paymentStatus = PaymentStatus.getByNote(order.getPaymentStatus());
        if (PaymentStatus.PAID.equals(paymentStatus)) {
            paidRadioButton.setSelected(true);
        } else {
            unpaidRadioButton.setSelected(true);
        }
    }

    public void onChangePayerIsSellerCheckbox(ActionEvent actionEvent) {
        if (payerIsSellerCheckbox.isSelected()) {
            payerTextField.setText(sellerTextField.getText());
        } else {
            payerTextField.setText("");
        }
    }

    public void printOrder(ActionEvent actionEvent) throws IOException {
        if (selectedOrder == null) {
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Page.ORDER.getFxml()));
        Parent root = fxmlLoader.load();
        OrderController orderController = fxmlLoader.getController();
        orderController.setValue(selectedOrder);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        boolean success = printService.printOrder(stage, root);
        if (!success) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi in phiếu cân");
            alert.setHeaderText(null);
            alert.setContentText("Có lỗi trọng quá trình xuất phiếu cân");
            alert.show();
        }
    }
}
