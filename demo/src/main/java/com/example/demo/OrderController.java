package com.example.demo;

import com.example.demo.model.Order;
import com.example.demo.utils.util.DateUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import static com.example.demo.utils.util.DateUtil.DD_MM_YYYY;

public class OrderController{
    @FXML
    private Label companyLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label mobileLabel;
    @FXML
    private Label indexLabel;
    @FXML
    private Label licensePlatesLabel;
    @FXML
    private Label sellerLabel;
    @FXML
    private Label buyerLabel;
    @FXML
    private Label cargoLabel;
    @FXML
    private Label createdLabel;
    @FXML
    private Label updatedTimeLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label totalWeightLabel;
    @FXML
    private Label vehicleWeightLabel;
    @FXML
    private Label cargoWeightLabel;
    @FXML
    private Label createdByLabel;

    public OrderController() {
    }

    public void setValue(Order order){
        if (order == null){
            return;
        }

        companyLabel.setText( "Trạm cân điện tử Phong Thái");
        addressLabel.setText("Đa Hội - Châu Khê- Từ Sơn - Bắc Ninh");
        mobileLabel.setText("0888400553");
        indexLabel.setText(String.valueOf(order.getIndex()));
        licensePlatesLabel.setText(order.getLicensePlates());
        sellerLabel.setText(order.getSeller());
        buyerLabel.setText(order.getBuyer());
        cargoLabel.setText(order.getCargoType());
        createdLabel.setText(DateUtil.convertToString(order.getCreatedAt(), DD_MM_YYYY));
        updatedTimeLabel.setText(DateUtil.convertToString(order.getUpdatedAt(), DD_MM_YYYY));
        amountLabel.setText(String.valueOf(order.getPaymentAmount()));
        totalWeightLabel.setText(String.valueOf(order.getTotalWeight()));
        vehicleWeightLabel.setText(String.valueOf(order.getVehicleWeight()));
        cargoWeightLabel.setText(String.valueOf(order.getCargoWeight()));
        createdByLabel.setText(order.getCreatedBy());
    }
}
