module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires commons.lang;
    requires jSerialComm;
    requires jasperreports;

    opens com.example.demo to javafx.fxml;
    opens com.example.demo.model to javafx.fxml;
    opens com.example.demo.data to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.model;
    exports com.example.demo.data;
    exports com.example.demo.controller;
    opens com.example.demo.controller to javafx.fxml;
}