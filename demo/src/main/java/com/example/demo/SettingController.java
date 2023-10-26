package com.example.demo;

import com.example.demo.dao.SettingDAO;
import com.example.demo.model.Setting;
import com.example.demo.model.WeightMoney;
import com.example.demo.service.IOService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
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
    private IOService ioService = new IOService();
    private ObservableList<Setting> settingObservableList;
    private Setting selectedSetting;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        keyCol.setCellValueFactory(new PropertyValueFactory("key"));
        valueCol.setCellValueFactory(new PropertyValueFactory("value"));
        settingObservableList = settingDAO.getAll();
        settingTable.setItems(settingObservableList);
        settingTable.setPlaceholder(new Label(""));
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

//    public void importOrder(ActionEvent actionEvent) throws FileNotFoundException {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
//        fileChooser.setTitle("Chọn file CSV");
//        File file = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());
//        boolean isSuccess = ioService.importData(file);
//        Alert alert = null;
//        String title = "Nhập dữ liệu dữ liệu";
//        if (isSuccess) {
//            alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle(title);
//            alert.setHeaderText(null);
//            alert.setContentText(title + " thành công");
//        } else {
//            alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle(title);
//            alert.setHeaderText(null);
//            alert.setContentText(title + " thất bại");
//        }
//        alert.show();
//    }
//
//    public void export(ActionEvent actionEvent) throws FileNotFoundException {
//        DirectoryChooser directoryChooser = new DirectoryChooser();
//        File selectedDirectory = directoryChooser.showDialog(((Node) actionEvent.getSource()).getScene().getWindow());
//        if (selectedDirectory == null) {
//            return;
//        }
//        boolean isSuccess = ioService.exportData(orderDAO.getOrderFilters(null,
//                        null,
//                        null,
//                        null,
//                        null,
//                        startDatePicker.getValue().atStartOfDay().withHour(0).withMinute(0).withSecond(0),
//                        endDatePicker.getValue().atStartOfDay().withHour(23).withMinute(59).withSecond(59)),
//                selectedDirectory.getAbsolutePath());
//        Alert alert = null;
//        String title = "Xuất dữ liệu";
//        if (isSuccess) {
//            alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle(title);
//            alert.setHeaderText(null);
//            alert.setContentText(title + " thành công");
//        } else {
//            alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle(title);
//            alert.setHeaderText(null);
//            alert.setContentText(title + " thất bại");
//        }
//        alert.show();
//    }
}
