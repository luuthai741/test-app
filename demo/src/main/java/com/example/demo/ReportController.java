package com.example.demo;

import com.example.demo.dao.OrderDAO;
import com.example.demo.model.Order;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    @FXML
    private StackPane stackPane;
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
    private TableColumn<Order, LocalDate> createdAtCol;
    final int MAX_ITEM = 45;
    final int NORMAL_ITEM = 53;
    private OrderDAO orderDAO = OrderDAO.getInstance();
    private ObservableList<Order> orders;

    public void setOrders(ObservableList<Order> orders) {
        this.orders = orders;
        indexCol.setCellValueFactory(new PropertyValueFactory("index"));
        licensePlatesCol.setCellValueFactory(new PropertyValueFactory("licensePlates"));
        sellerCol.setCellValueFactory(new PropertyValueFactory("seller"));
        buyerCol.setCellValueFactory(new PropertyValueFactory("buyer"));
        totalWeightCol.setCellValueFactory(new PropertyValueFactory("totalWeight"));
        vehicleWeightCol.setCellValueFactory(new PropertyValueFactory("vehicleWeight"));
        cargoWeightCol.setCellValueFactory(new PropertyValueFactory("cargoWeight"));
        createdAtCol.setCellValueFactory(new PropertyValueFactory("createdAt"));
        orderTable.setItems(orders);
        orderTable.setEditable(false);
        orderTable.setSelectionModel(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
