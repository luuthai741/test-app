package com.example.demo;

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
    @FXML
    private BorderPane settingPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void switchPage(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        Page page = Page.valueOf(button.getId());
        Parent newRoot = null;
        switch (page) {
            case INDEX:
                newRoot = FXMLLoader.load(getClass().getResource(INDEX.getFxml()));
                break;
            case ADMIN_HOME:
                newRoot = FXMLLoader.load(getClass().getResource(ADMIN_HOME.getFxml()));
                break;
            case LIST:
                if (!ConvertUtil.PAGES.contains(LIST.name())) {
                    newRoot = FXMLLoader.load(getClass().getResource(LIST.getFxml()));
                    Scene scene = new Scene(newRoot);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Danh sách mã cân");
                    stage.show();
                    ConvertUtil.PAGES.add(LIST.name());
                    stage.setOnCloseRequest(e -> {
                        ConvertUtil.PAGES.remove(LIST.name());
                    });
                    System.out.println("Mở trang");
                    return;
                }
                System.out.println("Đã mở trang");
                break;
            case DEBTOR:
                newRoot = FXMLLoader.load(getClass().getResource(DEBTOR.getFxml()));
                System.out.println("TEST DEBTOR");
                break;
            case SETTING:
                newRoot = FXMLLoader.load(getClass().getResource(SETTING.getFxml()));
                break;
            case WEIGHT_MONEY:
                newRoot = FXMLLoader.load(getClass().getResource(WEIGHT_MONEY.getFxml()));
                break;
        }
        borderPane.setCenter(newRoot);
    }

    public void switchVisibleSettingPane(ActionEvent actionEvent){
        settingPane.setVisible(!settingPane.isVisible());
    }
}
