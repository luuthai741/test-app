package com.example.demo;

import com.example.demo.dao.SettingDAO;
import com.example.demo.model.Order;
import com.example.demo.model.Setting;
import com.example.demo.utils.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import static com.example.demo.utils.constants.SettingKey.*;
import static com.example.demo.utils.util.DateUtil.DD_MM_YYYY;
import static com.example.demo.utils.util.DateUtil.DD_MM_YYYY_HH_MM_SS;

public class OrderController {
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
    @FXML
    private Label noteLabel;
    private SettingDAO settingDAO = SettingDAO.getInstance();

    public OrderController() {
    }

    public void setValue(Order order) {
        if (order == null) {
            return;
        }
        Setting compaynyNameSetting = settingDAO.getByKey(COMPANY_NAME);
        Setting phoneSetting = settingDAO.getByKey(PHONE);
        Setting addressSetting = settingDAO.getByKey(ADDRESS);
        companyLabel.setText(compaynyNameSetting == null ? "" : compaynyNameSetting.getValue());
        addressLabel.setText(phoneSetting == null ? "" : phoneSetting.getValue());
        mobileLabel.setText(addressSetting == null ? "" : addressSetting.getValue());
        indexLabel.setText(String.valueOf(order.getIndex()));
        licensePlatesLabel.setText(order.getLicensePlates());
        sellerLabel.setText(order.getSeller());
        buyerLabel.setText(order.getBuyer());
        cargoLabel.setText(order.getCargoType());
        createdLabel.setText(DateUtil.convertToString(order.getCreatedAt(), DD_MM_YYYY_HH_MM_SS));
        updatedTimeLabel.setText(DateUtil.convertToString(order.getUpdatedAt(), DD_MM_YYYY_HH_MM_SS));
        amountLabel.setText(String.valueOf(order.getPaymentAmount()));
        totalWeightLabel.setText(String.valueOf(order.getTotalWeight()));
        vehicleWeightLabel.setText(String.valueOf(order.getVehicleWeight()));
        cargoWeightLabel.setText(String.valueOf(order.getCargoWeight()));
        createdByLabel.setText(order.getCreatedBy());
        noteLabel.setText(order.getNote());
    }
}
