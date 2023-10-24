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
    }

    private boolean validate() {
        String value = valueTextField.getText();
        String message = "";
        if (value.isBlank()) {
            message = "Khối lượng kết thúc không thể nhỏ hơn bắt đầu";
        }
        if (selectedSetting == null) {
            message = "Vui lòng chọn cấu hình";
        }
        if (!message.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Thêm tiền cân thất bại");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.show();
            return false;
        }
        return true;
    }

    public void saveSetting(ActionEvent actionEvent) {
        Setting setting = selectedSetting;
        setting.setValue(valueTextField.getText());
        settingDAO.updateSetting(setting);
        cleanData();
    }

    private void cleanData() {
        settingObservableList = settingDAO.getAll();
        settingTable.setItems(settingObservableList);
        keyTextField.setText("");
        valueTextField.setText("");
        selectedSetting = null;
    }
}
