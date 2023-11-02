package com.example.demo.utils.constants;

import com.example.demo.HelloApplication;
import com.example.demo.data.ScreenScale;
import com.example.demo.utils.util.ConvertUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public enum Page {
    ADMIN_HOME("admin/home.fxml"),
    LIST("general/list.fxml"),
    INDEX("general/index.fxml"),
    FORM("general/form.fxml"),
    SETTING("admin/setting.fxml"),
    WEIGHT_MONEY("admin/weight_money.fxml"),
    LOG("admin/log.fxml"),;

    private String fxml;

    Page(String fxml) {
        this.fxml = fxml;
    }

    public String getFxml() {
        return fxml;
    }

    public static Parent getParenById(String id) throws IOException {
        Page page = Page.valueOf(id);
        Parent newRoot = null;
        switch (page) {
            case INDEX -> {
                newRoot = FXMLLoader.load(HelloApplication.class.getResource(INDEX.getFxml()));
            }
            case ADMIN_HOME -> {
                newRoot = FXMLLoader.load(HelloApplication.class.getResource(ADMIN_HOME.getFxml()));
            }
            case LIST -> {
                if (!ConvertUtil.PAGES.contains(LIST.name())){
                    newRoot = FXMLLoader.load(HelloApplication.class.getResource(LIST.getFxml()));
                    Scene scene = new Scene(newRoot);
                    Stage stage = new Stage();
                    ScreenScale screenScale = ScreenScale.getInstance();
                    stage.setX(screenScale.getWidth());
                    stage.setY(-screenScale.getHeight());
                    stage.setScene(scene);
                    stage.setTitle("Danh sách mã cân");
                    stage.show();
                    ConvertUtil.PAGES.add(LIST.name());
                    stage.setOnCloseRequest(e->{
                        ConvertUtil.PAGES.remove(LIST.name());
                    });
                    newRoot = null;
                }
            }
            case SETTING -> {
                newRoot = FXMLLoader.load(HelloApplication.class.getResource(SETTING.getFxml()));
            }
            case WEIGHT_MONEY-> {
                newRoot = FXMLLoader.load(HelloApplication.class.getResource(WEIGHT_MONEY.getFxml()));
            }
            case LOG-> {
                newRoot = FXMLLoader.load(HelloApplication.class.getResource(LOG.getFxml()));
            }
        }
        return newRoot;
    }
}
