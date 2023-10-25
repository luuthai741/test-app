package com.example.demo;

import com.example.demo.data.CurrentUser;
import com.example.demo.utils.constants.RoleType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.demo.utils.constants.RoleType.ADMIN;
import static com.example.demo.utils.constants.RoleType.USER;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;

    public void login() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Parent root;
        FXMLLoader fxmlLoader;
        CurrentUser currentUser = CurrentUser.getInstance();
        if (username.getText().equals("user") && password.getText().equals("user")) {
            fxmlLoader = new FXMLLoader(getClass().getResource("user.fxml"));
            root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            currentUser.setUsername(username.getText());
            currentUser.setRole(USER);
        } else if (username.getText().equals("admin") && password.getText().equals("admin")) {
            fxmlLoader = new FXMLLoader(getClass().getResource("admin.fxml"));
            root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            currentUser.setUsername(username.getText());
            currentUser.setRole(ADMIN);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Đăng nhập thất bại");
            alert.setHeaderText(null);
            alert.setContentText("Tên tài khoản hoặc mật khẩu không hợp lệ");
            alert.show();
        }
    }
}