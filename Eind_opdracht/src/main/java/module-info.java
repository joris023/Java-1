module com.example.eind_opdracht {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.example.eind_opdracht to javafx.fxml;
    exports com.example.eind_opdracht;
    exports com.example.eind_opdracht.model.Enums;
    opens com.example.eind_opdracht.model.Enums to javafx.fxml;
    exports com.example.eind_opdracht.model;
    opens com.example.eind_opdracht.model to javafx.fxml;
    exports com.example.eind_opdracht.controller;
    opens com.example.eind_opdracht.controller to javafx.fxml;
    exports com.example.eind_opdracht.dal;
    opens com.example.eind_opdracht.dal to javafx.fxml;
}