package com.example.eind_opdracht.controller;

import com.example.eind_opdracht.model.Showing;
import com.example.eind_opdracht.model.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmationController implements Initializable {

    @FXML private Label movieNameLabel;
    @FXML private Label dateAndTimeLabel;
    @FXML private Label numberTicketsLabel;
    @FXML private Label customerNameLabel;
    @FXML private CheckBox sixteenCheckbox;
    @FXML private Button confirmBtn;

    private Ticket ticket;
    private Boolean confirmed = false;

    public ConfirmationController(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        movieNameLabel.setText("Movie: " + ticket.getShowingTitle());
        dateAndTimeLabel.setText("Date and Time: " + ticket.getShowingStartTime());
        numberTicketsLabel.setText("Number of tickets: " + ticket.getNumberOfTickets());
        customerNameLabel.setText("Customer Name: " + ticket.getCustomerName());

        sixteenCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {

            confirmBtn.setDisable(!newValue);
        });
    }

    @FXML public void confirmPress(ActionEvent actionEvent) {
        if (sixteenCheckbox.isSelected()) {
            confirmed = true;  // Only confirm if the checkbox is selected
            closeStage();
        }
    }

    private void closeStage() {
        Stage stage = (Stage) movieNameLabel.getScene().getWindow();
        stage.close();
    }

    @FXML public void cancelPress(ActionEvent actionEvent) {
        confirmed = false;  // Ensure confirmation is false on cancel
        closeStage();
    }

    public boolean getConfirmation() {
        return confirmed;  // Return the value of confirmed (true or false)
    }
}
