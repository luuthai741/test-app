package com.example.demo.dao;

import com.example.demo.mapper.HistoryLogMapper;
import com.example.demo.model.HistoryLog;
import com.example.demo.model.Order;
import com.example.demo.query.HistoryLogQuery;
import com.example.demo.utils.constants.LogAction;
import com.example.demo.utils.constants.LogType;
import com.example.demo.utils.util.ConvertUtil;
import com.example.demo.utils.util.DateUtil;
import com.example.demo.utils.util.SqlUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.utils.constants.HistoryLogConstants.INSERT_LOG;
import static com.example.demo.utils.constants.HistoryLogConstants.SELECT_ALL;
import static com.example.demo.utils.util.ConvertUtil.replaceNullStringToBlank;

public class HistoryLogDAO {
    private static HistoryLogDAO instance;

    private HistoryLogDAO() {
    }

    private HistoryLogMapper historyLogMapper = HistoryLogMapper.getInstance();

    public static HistoryLogDAO getInstance() {
        if (instance == null) {
            instance = new HistoryLogDAO();
        }
        return instance;
    }

    public ObservableList<HistoryLog> getLogByFilters(String action, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        HistoryLogQuery historyLogQuery = new HistoryLogQuery();
        historyLogQuery.setAction(action);
        historyLogQuery.setDateTimeFrom(startDateTime);
        historyLogQuery.setDateTimeTo(endDateTime);
        ObservableList<HistoryLog> logs = FXCollections.observableList(new ArrayList<>());
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String sql = SELECT_ALL + historyLogQuery.getQueryCondition();
            ResultSet rs = sqlUtil.exeQuery(sql);
            List<HistoryLog> logList = historyLogMapper.mapToLog(rs);
            logs = FXCollections.observableList(logList);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return logs;
    }

    public void createLog(HistoryLog historyLog) {
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String query = String.format(INSERT_LOG,
                    historyLog.getLogType(),
                    historyLog.getContent(),
                    historyLog.getAction(),
                    DateUtil.convertToString(LocalDateTime.now(), DateUtil.DB_FORMAT)
            );
            sqlUtil.exeUpdate(query);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
    }

    public void createLogForOrder(Order oldOrder, Order updatedOrder, LogAction logAction) {
        String content = "";
        LogType logType = LogType.INFO;
        switch (logAction) {
            case CREATED_ORDER, CREATED_ORDER_MANUAL -> {
                content = String.format("Tạo mã cân: \n" +
                                "Tổng: %s -> %s, \n" +
                                "Bì:  %s -> %s, \n" +
                                "Hàng: %s -> %s, \n" +
                                "Biến số xe:  %s -> %s, \n" +
                                "Người bán:  %s -> %s, \n" +
                                "Người mua:  %s -> %s, \n" +
                                "Thanh toán:  %s -> %s, \n" +
                                "Tiền trả:  %s -> %s, \n" +
                                "Người trả:  %s -> %s \n",
                        oldOrder.getTotalWeight(), updatedOrder.getTotalWeight(),
                        oldOrder.getVehicleWeight(), updatedOrder.getVehicleWeight(),
                        oldOrder.getCargoWeight(), updatedOrder.getCargoWeight(),
                        replaceNullStringToBlank(oldOrder.getLicensePlates()), replaceNullStringToBlank(updatedOrder.getLicensePlates()),
                        replaceNullStringToBlank(oldOrder.getSeller()), replaceNullStringToBlank(updatedOrder.getSeller()),
                        replaceNullStringToBlank(oldOrder.getBuyer()), replaceNullStringToBlank(updatedOrder.getBuyer()),
                        replaceNullStringToBlank(oldOrder.getPaymentStatus()), replaceNullStringToBlank(updatedOrder.getPaymentStatus()),
                        oldOrder.getPaymentAmount(), updatedOrder.getPaymentAmount(),
                        replaceNullStringToBlank(oldOrder.getPayer()), replaceNullStringToBlank(updatedOrder.getPayer()));
            }
            case UPDATED_ORDER, UPDATED_ORDER_MANUAL -> {
                content = String.format("Cập nhật mã cân %s: \n" +
                                "Tổng: %s -> %s, \n" +
                                "Bì:  %s -> %s, \n" +
                                "Hàng: %s -> %s, \n" +
                                "Biến số xe:  %s -> %s, \n" +
                                "Người bán:  %s -> %s, \n" +
                                "Người mua:  %s -> %s, \n" +
                                "Thanh toán:  %s -> %s, \n" +
                                "Tiền trả:  %s -> %s, \n" +
                                "Người trả:  %s -> %s \n",
                        updatedOrder.getId(),
                        oldOrder.getTotalWeight(), updatedOrder.getTotalWeight(),
                        oldOrder.getVehicleWeight(), updatedOrder.getVehicleWeight(),
                        oldOrder.getCargoWeight(), updatedOrder.getCargoWeight(),
                        replaceNullStringToBlank(oldOrder.getLicensePlates()), replaceNullStringToBlank(updatedOrder.getLicensePlates()),
                        replaceNullStringToBlank(oldOrder.getSeller()), replaceNullStringToBlank(updatedOrder.getSeller()),
                        replaceNullStringToBlank(oldOrder.getBuyer()), replaceNullStringToBlank(updatedOrder.getBuyer()),
                        replaceNullStringToBlank(oldOrder.getPaymentStatus()), replaceNullStringToBlank(updatedOrder.getPaymentStatus()),
                        oldOrder.getPaymentAmount(), updatedOrder.getPaymentAmount(),
                        replaceNullStringToBlank(oldOrder.getPayer()), replaceNullStringToBlank(updatedOrder.getPayer()));
            }
            case DELETE_ORDER -> {
                content = "Xóa mã cân " + oldOrder.getId();
                logType = LogType.WARN;
            }
        }
        HistoryLog historyLog = new HistoryLog();
        historyLog.setLogType(logType.name());
        historyLog.setContent(content);
        historyLog.setAction(logAction.getNote());
        createLog(historyLog);
    }
}
