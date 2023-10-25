package com.example.demo;

import com.example.demo.dao.SettingDAO;
import com.example.demo.model.Setting;
import com.example.demo.utils.constants.SettingKey;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        createDefaultSettingKey();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("App");
        stage.setScene(scene);
        stage.show();
    }

    private void createDefaultSettingKey() {
        SettingDAO settingDAO = SettingDAO.getInstance();
        for (SettingKey settingKey : SettingKey.values()) {
            Setting setting = new Setting();
            setting.setKey(settingKey.name());
            setting.setValue(" ");
            settingDAO.createSetting(setting);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}