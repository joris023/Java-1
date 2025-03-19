package com.example.eind_opdracht.controller;

import com.example.eind_opdracht.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML private Label userLabel;
    @FXML private Label timeLabel;
    @FXML private Label roleLabel;

    private User user;

    public WelcomeController(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userLabel.setText("Welcome " + user.getUsername());
        roleLabel.setText("You are logged in as " + user.getRole().toString());
        timeLabel.setText("The current date and time is " + dateTimePicker());
    }
    private String dateTimePicker() {
        Date date = new Date();
        date = new Date(date.getTime());
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return formatter.format(date);
    }
}
