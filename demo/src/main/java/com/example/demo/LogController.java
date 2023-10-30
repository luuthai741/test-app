package com.example.demo;

import com.example.demo.dao.HistoryLogDAO;
import com.example.demo.model.HistoryLog;
import com.example.demo.model.Order;
import com.example.demo.utils.constants.LogAction;
import com.example.demo.utils.util.DateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.example.demo.utils.util.DateUtil.DD_MM_YYYY_HH_MM_SS;

public class LogController implements Initializable {
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<String> typeCombobox;
    @FXML
    private TableView<HistoryLog> logTable;
    @FXML
    private TableColumn<?, ?> idCol;
    @FXML
    private TableColumn<?, ?> typeCol;
    @FXML
    private TableColumn<?, ?> actionCol;
    @FXML
    private TableColumn<?, ?> contentCol;
    @FXML
    private TableColumn<HistoryLog, String> createdAtCol;
    private HistoryLogDAO historyLogDAO = HistoryLogDAO.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        typeCombobox.setItems(LogAction.getActions());
        typeCombobox.setValue(LogAction.ALL.getNote());
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        typeCol.setCellValueFactory(new PropertyValueFactory("logType"));
        actionCol.setCellValueFactory(new PropertyValueFactory("action"));
        contentCol.setCellValueFactory(new PropertyValueFactory("content"));
        createdAtCol.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(DateUtil.convertToString(cellData.getValue().getCreatedAt(), DD_MM_YYYY_HH_MM_SS)));
        logTable.setPlaceholder(new Label(""));
        logTable.setItems(historyLogDAO.getLogByFilters(typeCombobox.getValue(),
                startDatePicker.getValue().atStartOfDay().withHour(0).withMinute(0).withSecond(0),
                endDatePicker.getValue().atStartOfDay().withHour(23).withMinute(59).withSecond(59)));
    }

    public void report(){

    }
    public void search(){
        if (endDatePicker.getValue().isBefore(startDatePicker.getValue())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Lỗi tìm kiếm");
            alert.setHeaderText(null);
            alert.setContentText("Ngày kết thúc không được nhỏ hơn ngày bắt đầu!");
            alert.show();
            return;
        }
        logTable.setItems(historyLogDAO.getLogByFilters(typeCombobox.getValue(),
                startDatePicker.getValue().atStartOfDay().withHour(0).withMinute(0).withSecond(0),
                endDatePicker.getValue().atStartOfDay().withHour(23).withMinute(59).withSecond(59)));
    }
}
