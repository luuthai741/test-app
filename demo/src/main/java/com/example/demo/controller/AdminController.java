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
        Parent newRoot = Page.getParenById(button.getId());
        if (newRoot != null) {
            borderPane.setCenter(newRoot);
        }
    }
}
