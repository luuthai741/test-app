package com.example.demo;

import com.example.demo.utils.constants.Page;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

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
                System.out.println("Can xe");
            }
            case ADMIN_HOME -> {
                newRoot = FXMLLoader.load(getClass().getResource(ADMIN_HOME.getFxml()));
                System.out.println("TEST HOME");
            }
            case LIST -> {
                newRoot = FXMLLoader.load(getClass().getResource(LIST.getFxml()));
                System.out.println("TEST LIST");
            }
            case DEBTOR -> {
                newRoot = FXMLLoader.load(getClass().getResource(DEBTOR.getFxml()));
                System.out.println("TEST DEBTOR");
            }
            case FORM -> {
                newRoot = FXMLLoader.load(getClass().getResource(FORM.getFxml()));
                System.out.println("TEST FORM");
            }
        }
        borderPane.setCenter(newRoot);
    }
}
