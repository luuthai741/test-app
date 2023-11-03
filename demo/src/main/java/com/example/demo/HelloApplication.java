package com.example.demo;

import com.example.demo.dao.SettingDAO;
import com.example.demo.data.ScreenScale;
import com.example.demo.model.Setting;
import com.example.demo.utils.constants.SettingKey;
import com.example.demo.utils.util.ConvertUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Map;

import static com.example.demo.utils.constants.Page.LIST;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        ScreenScale screenScale = ScreenScale.getInstance();
        screenScale.setHeight(bounds.getHeight());
        screenScale.setWidth(bounds.getWidth());
        System.out.println("Width " + screenScale.getWidth() + ", Height " + screenScale.getHeight());
        createDefaultSettingKey();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("App");
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            System.exit(0);
        });
        stage.show();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Map<String,String> vars = System.getenv();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : vars.entrySet()) {
            stringBuilder.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
        }
        alert.setHeaderText("test");
        alert.setContentText(stringBuilder.toString());
        alert.show();
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
}