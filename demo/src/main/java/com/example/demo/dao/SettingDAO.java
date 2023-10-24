package com.example.demo.dao;

import com.example.demo.mapper.SettingMapper;
import com.example.demo.model.Setting;
import com.example.demo.utils.constants.SettingKey;
import com.example.demo.utils.util.SqlUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.utils.constants.SettingConstants.*;
import static com.example.demo.utils.constants.SettingConstants.SELECT_ALL;


public class SettingDAO {
    private static SettingDAO instance;
    private SettingMapper settingMapper = SettingMapper.getInstance();

    private SettingDAO() {
    }

    public static SettingDAO getInstance() {
        if (instance == null) {
            instance = new SettingDAO();
        }
        return instance;
    }

    public ObservableList<Setting> getAll() {
        SqlUtil sqlUtil = new SqlUtil();
        ObservableList<Setting> settingObservableList = FXCollections.observableList(new ArrayList<>());
        try {
            sqlUtil.connect();
            String sql = SELECT_ALL;
            ResultSet rs = sqlUtil.exeQuery(sql);
            List<Setting> moneyList = settingMapper.mapToSetting(rs);
            settingObservableList = FXCollections.observableList(moneyList);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return settingObservableList;
    }

    public void createSetting(Setting setting) {
        Setting dbSetting = getByKey(SettingKey.valueOf(setting.getKey()));
        if (dbSetting != null) {
            return;
        }
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String sql = String.format(INSERT_SETTING, setting.getValue(), setting.getValue());
            sqlUtil.exeQuery(sql);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
    }

    public Setting updateSetting(Setting setting) {
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String sql = String.format(UPDATE_SETTING_BY_KEY, setting.getValue(), setting.getKey());
            sqlUtil.exeQuery(sql);
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return setting;
    }

    public Setting getByKey(SettingKey settingKey) {
        Setting setting = null;
        SqlUtil sqlUtil = new SqlUtil();
        try {
            sqlUtil.connect();
            String sql = String.format(SELECT_BY_KEY, setting.getKey());
            ResultSet rs = sqlUtil.exeQuery(sql);
            List<Setting> settings = settingMapper.mapToSetting(rs);
            if (settings.size() > 0) {
                setting = settings.get(0);
            }
        } catch (Exception e) {

        } finally {
            sqlUtil.disconnect();
        }
        return setting;
    }
}
