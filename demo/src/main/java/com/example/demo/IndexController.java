package com.example.demo;

import com.example.demo.dao.OrderDAO;
import com.example.demo.model.Order;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class IndexController implements Initializable {
    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, Integer> indexCol;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OrderDAO orderDAO = OrderDAO.getInstance();
        ObservableList<Order> orders = orderDAO.getOrders();
        indexCol.setCellValueFactory(new PropertyValueFactory<Order,Integer>("index"));
        sellerCol.setCellValueFactory(new PropertyValueFactory<Order,String>("seller"));
        buyerCol.setCellValueFactory(new PropertyValueFactory<Order,String>("buyer"));
        totalWeightCol.setCellValueFactory(new PropertyValueFactory<Order,Double>("totalWeight"));
        vehicleWeightCol.setCellValueFactory(new PropertyValueFactory<Order,Double>("vehicleWeight"));
        cargoWeightCol.setCellValueFactory(new PropertyValueFactory<Order,Double>("cargoWeight"));
        cargoCol.setCellValueFactory(new PropertyValueFactory<Order,String>("cargoType"));
        createdAtCol.setCellValueFactory(new PropertyValueFactory<Order,LocalDate>("createdAt"));
        orderTable.setItems(orders);
    }
}
