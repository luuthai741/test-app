package com.example.demo;

import com.example.demo.dao.SettingDAO;
import com.example.demo.model.Setting;
import com.example.demo.model.WeightMoney;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang.StringUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    @FXML
    private TextField keyTextField;
    @FXML
    private TextField valueTextField;
    @FXML
    private TableView<Setting> settingTable;
    @FXML
    private TableColumn<Setting, String> keyCol;
    @FXML
    private TableColumn<Setting, String> valueCol;

    private SettingDAO settingDAO = SettingDAO.getInstance();
    private ObservableList<Setting> settingObservableList;
    private Setting selectedSetting;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        keyCol.setCellValueFactory(new PropertyValueFactory("key"));
        valueCol.setCellValueFactory(new PropertyValueFactory("value"));
        settingObservableList = settingDAO.getAll();
        settingTable.setItems(settingObservableList);
        settingTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedSetting = newValue;
            if (selectedSetting != null) {
                keyTextField.setText(selectedSetting.getKey());
                valueTextField.setText(selectedSetting.getValue());
            }
        });
    }

    private boolean validate() {
        String value = valueTextField.getText();
        String message = "";
        if (selectedSetting == null) {
            message = "Vui lòng chọn cấu hình";
        } else if (StringUtils.isBlank(value)) {
            message = "Vui lòng nhập giá trị";
        }
        if (StringUtils.isNotBlank(message)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Cập nhật cấu hình thất bại");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.show();
            return false;
        }
        return true;
    }

    public void saveSetting(ActionEvent actionEvent) {
        if (!validate()) {
            return;
        }
        Setting setting = selectedSetting;
        setting.setValue(valueTextField.getText());
        settingDAO.updateSetting(setting);
        cleanData();
        settingTable.getSelectionModel().clearSelection();
    }

    private void cleanData() {
        settingObservableList = settingDAO.getAll();
        settingTable.setItems(settingObservableList);
        keyTextField.setText("");
        valueTextField.setText("");
        selectedSetting = null;
    }
}
