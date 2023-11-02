package com.example.demo;

import com.example.demo.dao.HistoryLogDAO;
import com.example.demo.dao.OrderDAO;
import com.example.demo.dao.SettingDAO;
import com.example.demo.model.HistoryLog;
import com.example.demo.model.Order;
import com.example.demo.service.ReportService;
import com.example.demo.utils.constants.LogAction;
import com.example.demo.utils.constants.LogType;
import com.example.demo.utils.constants.OrderStatus;
import com.example.demo.utils.constants.PaymentStatus;
import com.example.demo.utils.util.ConvertUtil;
import com.example.demo.utils.util.DateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static com.example.demo.utils.constants.Page.FORM;
import static com.example.demo.utils.util.DateUtil.DD_MM_YYYY;

public class ItemController implements Initializable {
    @FXML
    private AnchorPane mainPane;
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
    private TableColumn<Order, String> createdAtCol;

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
    private TableColumn<Order, String> updatedCol;

    @FXML
    private TableColumn<?, ?> vehicleWeightCol;

    private OrderDAO orderDAO = OrderDAO.getInstance();
    private HistoryLogDAO historyLogDAO = HistoryLogDAO.getInstance();
    private SettingDAO settingDAO = SettingDAO.getInstance();
    private ReportService reportService = ReportService.getInstance();
    private ObservableList<Order> orders;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        indexCol.setCellValueFactory(new PropertyValueFactory("index"));
        licensePlatesCol.setCellValueFactory(new PropertyValueFactory("licensePlates"));
        sellerCol.setCellValueFactory(new PropertyValueFactory("seller"));
        buyerCol.setCellValueFactory(new PropertyValueFactory("buyer"));
        sellerCol.setEditable(true);
        totalWeightCol.setCellValueFactory(new PropertyValueFactory("totalWeight"));
        vehicleWeightCol.setCellValueFactory(new PropertyValueFactory("vehicleWeight"));
        cargoWeightCol.setCellValueFactory(new PropertyValueFactory("cargoWeight"));
        cargoCol.setCellValueFactory(new PropertyValueFactory("cargoType"));
        createdAtCol.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(DateUtil.convertToString(cellData.getValue().getCreatedAt(), DD_MM_YYYY)));
        updatedCol.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(DateUtil.convertToString(cellData.getValue().getUpdatedAt(), DD_MM_YYYY)));
        paymentStatusCol.setCellValueFactory(new PropertyValueFactory("paymentStatus"));
        amountCol.setCellValueFactory(new PropertyValueFactory("paymentAmount"));
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        statusComboBox.setItems(OrderStatus.getIndexStatus());
        statusComboBox.setValue(OrderStatus.ALL.getNote());
        paymentStatusComboBox.setItems(PaymentStatus.getItemStatus());
        paymentStatusComboBox.setValue(PaymentStatus.ALL.getNote());
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Chi tiết");
        MenuItem deleteItem = new MenuItem("Xóa");
        editItem.setOnAction(e -> {
            try {
                openPopup(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        orderTable.setPlaceholder(new Label("Hiện chưa có mã cân nào"));
        deleteItem.setOnAction(e -> {
            Order order = orderTable.getSelectionModel().getSelectedItem();
            orderDAO.deleteOrder(order);
            orderTable.setItems(orderDAO.getOrderFilters(null,
                    sellerTextField.getText(),
                    buyerTextField.getText(),
                    statusComboBox.getValue(),
                    paymentStatusComboBox.getValue(),
                    startDatePicker.getValue().atStartOfDay().withHour(0).withMinute(0).withSecond(0),
                    endDatePicker.getValue().atStartOfDay().withHour(23).withMinute(59).withSecond(59)));
            historyLogDAO.createLogForOrder(order, order, LogAction.DELETE_ORDER);

        });
        contextMenu.getItems().add(editItem);
        contextMenu.getItems().add(deleteItem);
        orders = orderDAO.getOrderFilters(null,
                sellerTextField.getText(),
                buyerTextField.getText(),
                statusComboBox.getValue(),
                paymentStatusComboBox.getValue(),
                startDatePicker.getValue().atStartOfDay().withHour(0).withMinute(0).withSecond(0),
                endDatePicker.getValue().atStartOfDay().withHour(23).withMinute(59).withSecond(59));
        orderTable.setContextMenu(contextMenu);
        orderTable.setItems(orders);
    }

    public void search() {
        if (endDatePicker.getValue().isBefore(startDatePicker.getValue())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Lỗi tìm kiếm");
            alert.setHeaderText(null);
            alert.setContentText("Ngày kết thúc không được nhỏ hơn ngày bắt đầu!");
            alert.show();
            return;
        }
        orders = orderDAO.getOrderFilters(null,
                sellerTextField.getText(),
                buyerTextField.getText(),
                statusComboBox.getValue(),
                paymentStatusComboBox.getValue(),
                startDatePicker.getValue().atStartOfDay().withHour(0).withMinute(0).withSecond(0),
                endDatePicker.getValue().atStartOfDay().withHour(23).withMinute(59).withSecond(59));
        orderTable.setItems(orders);
    }

    public void openPopup(ActionEvent actionEvent) throws IOException {
        if (!ConvertUtil.PAGES.contains(FORM.name())) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FORM.getFxml()));
            Parent root = fxmlLoader.load();
            FormController formController = fxmlLoader.getController();
            formController.setValue(orderDAO.getById(String.valueOf(orderTable.getSelectionModel().getSelectedItem().getId())));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Chỉnh sửa mã cân");
            stage.show();
            ConvertUtil.PAGES.add(FORM.name());
            mainPane.setDisable(true);
            stage.setOnCloseRequest(e -> {
                ConvertUtil.PAGES.remove(FORM.name());
                search();
                mainPane.setDisable(false);
            });
        }
    }

    public void report() throws JRException {
        ObservableList<Order> ordersFilter = orderDAO.getOrderFilters(null,
                sellerTextField.getText(),
                buyerTextField.getText(),
                statusComboBox.getValue(),
                paymentStatusComboBox.getValue(),
                startDatePicker.getValue().atStartOfDay().withHour(0).withMinute(0).withSecond(0),
                endDatePicker.getValue().atStartOfDay().withHour(23).withMinute(59).withSecond(59));
        if (ordersFilter.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thống kê mã cân");
            alert.setHeaderText(null);
            alert.setContentText("Không có mã cân nào");
            alert.show();
            return;
        }
        reportService.printOrders(ordersFilter, startDatePicker.getValue(), endDatePicker.getValue());
    }

    public void updatePaidPaymentStatus() {
        if (orders.size() == 0) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cập nhật trả tiền mã cân");
        alert.setHeaderText(null);
        alert.setContentText("Sau khi thanh toán mã cân, bạn sẽ không thể cập nhật được người trả, số tiền và trạng thái thanh toán của mã cân.\nBạn chắc chắn chứ?");
        if (alert.showAndWait().get() != ButtonType.OK) {
            return;
        }
        StringBuilder successBuilder = new StringBuilder();
        StringBuilder failBuilder = new StringBuilder();
        for (Order order : orders) {
            if (!order.getPaymentStatus().equalsIgnoreCase(PaymentStatus.UNPAID.getNote())) {
                continue;
            }
            if (order.getPaymentAmount() <= 0) {
                failBuilder.append("Mã cân: ")
                        .append(order.getIndex())
                        .append(" tạo ngày ")
                        .append(DateUtil.convertToString(order.getCreatedAt(), DD_MM_YYYY))
                        .append(" với số tiền thanh toán ")
                        .append(order.getPaymentAmount()).append(".\n");
                continue;
            }
            order.setStatus(PaymentStatus.PAID.getNote());
            order.setUpdatedAt(LocalDateTime.now());
            successBuilder.append("Mã cân: ")
                    .append(order.getIndex())
                    .append(" tạo ngày ")
                    .append(DateUtil.convertToString(order.getCreatedAt(), DD_MM_YYYY))
                    .append(".\n");
            orderDAO.updateOrder(order);
        }
        String successMessage = successBuilder.toString();
        String failedMessage = failBuilder.toString();
        if (StringUtils.isBlank(successMessage) && StringUtils.isBlank(failedMessage)){
            return;
        }
        String responseMessage = "";
        if (StringUtils.isNotBlank(successMessage)){
            HistoryLog historyLog = new HistoryLog();
            historyLog.setLogType(LogType.WARN.name());
            historyLog.setContent(successMessage);
            historyLog.setAction(LogAction.UPDATED_PAID_ORDERS.getNote());
            historyLogDAO.createLog(historyLog);
            responseMessage = "Cập nhật thành công \n" + successMessage;
        }
        if (StringUtils.isNotBlank(failedMessage)){
            responseMessage += "Cập nhật thất bại \n" + failedMessage;
        }
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo cập nhật thanh toán");
        alert.setHeaderText(null);
        alert.setContentText(responseMessage);
        alert.show();
    }
}
