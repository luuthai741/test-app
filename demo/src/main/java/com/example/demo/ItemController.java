package com.example.demo;

import com.example.demo.dao.OrderDAO;
import com.example.demo.model.Order;
import com.example.demo.utils.constants.OrderStatus;
import com.example.demo.utils.constants.PaymentStatus;
import com.example.demo.utils.util.ConvertUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.example.demo.utils.constants.Page.FORM;
import static com.example.demo.utils.constants.Page.REPORT;

public class ItemController implements Initializable {
    @FXML
    private TableColumn<?, ?> amountCol;

    @FXML
    private TableColumn<?, ?> buyerCol;

    @FXML
    private TextField buyerTextField;

    @FXML
    private TableColumn<?, ?> cargoCol;

    @FXML
    private TableColumn<?, ?> cargoWeightCol;

    @FXML
    private TableColumn<?, ?> createdAtCol;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TableColumn<?, ?> indexCol;

    @FXML
    private TableColumn<?, ?> licensePlatesCol;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private ComboBox<String> paymentStatusComboBox;

    @FXML
    private TableColumn<?, ?> paymentStatusCol;

    @FXML
    private TableColumn<?, ?> sellerCol;

    @FXML
    private TextField sellerTextField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private TableColumn<?, ?> totalWeightCol;

    @FXML
    private TableColumn<?, ?> updatedCol;

    @FXML
    private TableColumn<?, ?> vehicleWeightCol;

    private OrderDAO orderDAO = OrderDAO.getInstance();
    private ObservableList<Order> orders;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        indexCol.setCellValueFactory(new PropertyValueFactory("index"));
        licensePlatesCol.setCellValueFactory(new PropertyValueFactory("licensePlates"));
        sellerCol.setCellValueFactory(new PropertyValueFactory("seller"));
        buyerCol.setCellValueFactory(new PropertyValueFactory("buyer"));
        sellerCol.setEditable(true);
        totalWeightCol.setCellValueFactory(new PropertyValueFactory("totalWeight"));
        vehicleWeightCol.setCellValueFactory(new PropertyValueFactory("vehicleWeight"));
        cargoWeightCol.setCellValueFactory(new PropertyValueFactory("cargoWeight"));
        cargoCol.setCellValueFactory(new PropertyValueFactory("cargoType"));
        createdAtCol.setCellValueFactory(new PropertyValueFactory("createdAt"));
        updatedCol.setCellValueFactory(new PropertyValueFactory("updatedAt"));
        paymentStatusCol.setCellValueFactory(new PropertyValueFactory("paymentStatus"));
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        statusComboBox.setItems(OrderStatus.getIndexStatus());
        statusComboBox.setValue(OrderStatus.ALL.getNote());
        paymentStatusComboBox.setItems(PaymentStatus.getItemStatus());
        paymentStatusComboBox.setValue(PaymentStatus.ALL.getNote());
        ContextMenu contextMenu = new ContextMenu();
        MenuItem detailMenuItem = new MenuItem("Chỉnh sửa");
        detailMenuItem.setOnAction(e -> {
            try {
                openPopup(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        contextMenu.getItems().add(detailMenuItem);
        orders = orderDAO.getOrderFilters(null,
                sellerTextField.getText(),
                buyerTextField.getText(),
                statusComboBox.getValue(),
                paymentStatusComboBox.getValue(),
                startDatePicker.getValue().atStartOfDay().withHour(0).withMinute(0).withSecond(0),
                endDatePicker.getValue().atStartOfDay().withHour(23).withMinute(59).withSecond(59));
        orderTable.setContextMenu(contextMenu);
        orderTable.setItems(orders);
    }

    public void report(ActionEvent actionEvent) throws IOException {
        if (!ConvertUtil.PAGES.contains(REPORT.name())) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(REPORT.getFxml()));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Thống kê");
            stage.show();
            ConvertUtil.PAGES.add(REPORT.name());
            stage.setOnCloseRequest(e -> {
                ConvertUtil.PAGES.remove(REPORT.name());
            });
        }
    }

    public void search(ActionEvent actionEvent) {
        if (endDatePicker.getValue().equals(startDatePicker.getValue())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Lỗi tìm kiếm");
            alert.setHeaderText(null);
            alert.setContentText("Ngày kết thúc không được nhỏ hơn ngày bắt đầu!");
            alert.show();
            return;
        }
        orders = orderDAO.getOrderFilters(null,
                sellerTextField.getText(),
                buyerTextField.getText(),
                statusComboBox.getValue(),
                paymentStatusComboBox.getValue(),
                startDatePicker.getValue().atStartOfDay().withHour(0).withMinute(0).withSecond(0),
                endDatePicker.getValue().atStartOfDay().withHour(23).withMinute(59).withSecond(59));
        orderTable.setItems(orders);
    }

    public void openPopup(ActionEvent actionEvent) throws IOException {
        if (!ConvertUtil.PAGES.contains(FORM.name())) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FORM.getFxml()));
            Parent root = fxmlLoader.load();
            FormController formController = fxmlLoader.getController();
            formController.setValue(orderTable.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Chỉnh sửa mã cân");
            stage.show();
            ConvertUtil.PAGES.add(FORM.name());
            stage.setOnCloseRequest(e -> {
                ConvertUtil.PAGES.remove(FORM.name());
            });
        }
    }
}
