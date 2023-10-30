package com.example.demo.utils.constants;

public enum SettingKey {
    COMPANY_NAME("Tên công ty"),
    PHONE("Số liện hệ"),
    ADDRESS("Địa chỉ"),
    COM_PORT("Cổng COM");

    private String note;

    SettingKey(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public static SettingKey getByNote(String note) {
        for (SettingKey settingKey : SettingKey.values()) {
            if (settingKey.getNote().equals(note)) {
                return settingKey;
            }
        }
        return null;
    }

    public static SettingKey getByName(String name){
        for (SettingKey settingKey : SettingKey.values()) {
            if (settingKey.name().equalsIgnoreCase(name)) {
                return settingKey;
            }
        }
        return null;
    }
}
