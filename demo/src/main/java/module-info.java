module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires commons.lang;
    requires jSerialComm;

    opens com.example.demo to javafx.fxml;
    opens com.example.demo.model to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.model;
}