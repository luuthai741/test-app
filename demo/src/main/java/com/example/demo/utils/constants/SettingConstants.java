package com.example.demo.utils.constants;

public class SettingConstants {
    public static final String SELECT_ALL = "SELECT * FROM setting";
    public static final String INSERT_SETTING = "INSERT INTO setting(KEY, VALUE) VALUE (%s,%s,%s)";
    public static final String UPDATE_SETTING_BY_KEY = "UPDATE setting SET VALUE = %s WHERE KEY = %s";
    public static final String SELECT_BY_KEY = "SELECT * FROM setting WHERE KEY = %s";
}
