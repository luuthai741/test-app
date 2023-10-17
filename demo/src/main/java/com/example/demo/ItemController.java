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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Order> orders = orderDAO.getOrders();
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
        orderTable.setItems(orders);
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        statusComboBox.setItems(OrderStatus.getIndexStatus());
        statusComboBox.setValue(OrderStatus.ALL.getNote());
        paymentStatusComboBox.setItems(PaymentStatus.getItemStatus());
        paymentStatusComboBox.setValue(PaymentStatus.ALL.getNote());
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Chỉnh sửa");
        deleteMenuItem.setOnAction(e -> {
            try {
                openPopup(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        contextMenu.getItems().add(deleteMenuItem);
        orderTable.setContextMenu(contextMenu);
    }

    public void report(ActionEvent actionEvent) {

    }

    public void search(ActionEvent actionEvent) {

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
