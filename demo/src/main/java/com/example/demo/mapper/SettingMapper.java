package com.example.demo.mapper;

import com.example.demo.model.Order;
import com.example.demo.model.Setting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SettingMapper {
    private static SettingMapper instance;

    private SettingMapper() {
    }

    public static SettingMapper getInstance() {
        if (instance == null) {
            instance = new SettingMapper();
        }
        return instance;
    }

    public List<Setting> mapToSetting(ResultSet resultSet) throws SQLException {
        List<Setting> settings = new ArrayList<>();
        while (resultSet.next()) {
            Setting setting = new Setting();
            setting.setKey(resultSet.getString("KEY"));
            setting.setValue(resultSet.getString("VALUE"));
            settings.add(setting);
        }
        return settings;
    }
}
