package com.example.demo;

import com.example.demo.dao.HistoryLogDAO;
import com.example.demo.dao.OrderDAO;
import com.example.demo.dao.SettingDAO;
import com.example.demo.data.CurrentUser;
import com.example.demo.data.OrderDTO;
import com.example.demo.model.Order;
import com.example.demo.model.Setting;
import com.example.demo.service.ReportService;
import com.example.demo.utils.constants.*;
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
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.lang.ObjectUtils;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.utils.constants.Page.FORM;
import static com.example.demo.utils.constants.SettingKey.*;
import static com.example.demo.utils.util.DateUtil.DD_MM_YYYY_HH_MM_SS;

public class ItemController implements Initializable {
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

    @FXML
    private Button importButton;

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
        createdAtCol.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(DateUtil.convertToString(cellData.getValue().getCreatedAt(), DD_MM_YYYY_HH_MM_SS)));
        updatedCol.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(DateUtil.convertToString(cellData.getValue().getUpdatedAt(), DD_MM_YYYY_HH_MM_SS)));
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
        if (!RoleType.ADMIN.equals(CurrentUser.getInstance().getRole())) {
            importButton.setDisable(true);
        }
    }

    public void search(ActionEvent actionEvent) {
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
            formController.setValue(orderTable.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Chỉnh sửa mã cân");
            stage.show();
            ConvertUtil.PAGES.add(FORM.name());
            stage.setOnCloseRequest(e -> {
                ConvertUtil.PAGES.remove(FORM.name());
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
}
