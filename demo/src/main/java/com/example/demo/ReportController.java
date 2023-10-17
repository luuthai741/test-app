package com.example.demo;

import com.example.demo.dao.OrderDAO;
import com.example.demo.model.Order;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    @FXML
    private ListView indexListView;
    @FXML
    private ListView sellerListView;
    @FXML
    private ListView buyerListView;
    private OrderDAO orderDAO = OrderDAO.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Order> orders = orderDAO.getOrders();
        indexListView.setEditable(false);
        sellerListView.setEditable(false);
        buyerListView.setEditable(false);
        indexListView.setSelectionModel(null);
        sellerListView.setSelectionModel(null);
        buyerListView.setSelectionModel(null);
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            indexListView.getItems().add(i + 1);
            sellerListView.getItems().add(order.getSeller());
            buyerListView.getItems().add(order.getBuyer());
        }
        indexListView.setPrefHeight(indexListView.getItems().size()*22.6);
        sellerListView.setPrefHeight(indexListView.getItems().size()*22.6);
        buyerListView.setPrefHeight(indexListView.getItems().size()*22.6);
    }
}
