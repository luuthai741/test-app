package com.example.demo;

import com.example.demo.data.ScreenScale;
import com.example.demo.utils.constants.Page;
import com.example.demo.utils.util.ConvertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.demo.utils.constants.Page.*;

public class AdminController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void switchPage(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        Page page = Page.valueOf(button.getId());
        Parent newRoot = null;
        switch (page) {
            case INDEX -> {
                newRoot = FXMLLoader.load(getClass().getResource(INDEX.getFxml()));
            }
            case ADMIN_HOME -> {
                newRoot = FXMLLoader.load(getClass().getResource(ADMIN_HOME.getFxml()));
            }
            case LIST -> {
                if (!ConvertUtil.PAGES.contains(LIST.name())){
                    newRoot = FXMLLoader.load(getClass().getResource(LIST.getFxml()));
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
                }
                return;
            }
            case SETTING -> {
                newRoot = FXMLLoader.load(getClass().getResource(SETTING.getFxml()));
            }
            case WEIGHT_MONEY-> {
                newRoot = FXMLLoader.load(getClass().getResource(WEIGHT_MONEY.getFxml()));
            }
            case LOG-> {
                newRoot = FXMLLoader.load(getClass().getResource(LOG.getFxml()));
            }
        }
        borderPane.setCenter(newRoot);
    }
}
