package com.example.demo.controller;

import com.example.demo.HelloApplication;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.demo.utils.constants.Page.*;

public class UserController implements Initializable {
    @FXML
    private BorderPane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Parent newRoot = null;
        try {
            newRoot = FXMLLoader.load(HelloApplication.class.getResource(INDEX.getFxml()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainPane.setCenter(newRoot);
    }

    public void switchPage(ActionEvent actionEvent) throws IOException {
        MenuItem menu = (MenuItem) actionEvent.getSource();
        Parent newRoot = Page.getParenById(menu.getId());
        if (newRoot != null) {
            mainPane.setCenter(newRoot);
        }
    }
}
