package com.example.eind_opdracht.controller;

import com.example.eind_opdracht.model.Showing;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddShowingController implements Initializable {

    @FXML private DatePicker startDatePicker;
    @FXML private TextField startTimePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private TextField endTimePicker;
    @FXML private TextField titleShowingTextField;
    @FXML private TextField durationTextField;
    @FXML private CheckBox sixteenPlusBtn;

    private Showing newShowing;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        startDatePicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                LocalDate startDate = startDatePicker.getValue();
                if (startDate != null) {
                    endDatePicker.setValue(startDate);
                }
            }
        });

        durationTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String inputText = durationTextField.getText();
                if (inputText != null && !inputText.isEmpty()) {
                    int duration = Integer.parseInt(inputText);
                    try {
                        LocalTime startTime = LocalTime.parse(startTimePicker.getText(), formatter);
                        LocalTime endTime = startTime.plusMinutes(duration);
                        String formattedTime = formatter.format(endTime);
                        endTimePicker.setText(formattedTime);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public Showing getNewShowing() {
        return newShowing;
    }

    private void addShowing() {
        try {
            if(!isFilled()) {
                throw new Exception("ERROR: Vul alle boxen goed in");
            }
            String titleShowingValue = titleShowingTextField.getText();
            LocalDate startDateValue = startDatePicker.getValue();
            LocalTime startTimeValue = LocalTime.parse(startTimePicker.getText(), formatter);
            LocalDate endDateValue = endDatePicker.getValue();
            LocalTime endTimeValue = LocalTime.parse(endTimePicker.getText(), formatter);

            LocalDateTime startDateTime = LocalDateTime.of(startDateValue, startTimeValue);
            LocalDateTime endDateTime = LocalDateTime.of(endDateValue, endTimeValue);

            Boolean sixteenPlusBtnValue = sixteenPlusBtn.isSelected();

            if (startDateTime.isAfter(endDateTime)) {
                throw new Exception("ERROR: Startdatum kan niet later zijn dan de einddatum.");
            }

            newShowing = new Showing(startDateTime, endDateTime, titleShowingValue, 72, sixteenPlusBtnValue);

            closeStage();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private boolean isFilled() {
        if (titleShowingTextField.getText() == null || titleShowingTextField.getText().isEmpty() ||
                startDatePicker.getValue() == null ||
                startTimePicker.getText() == null || startTimePicker.getText().isEmpty() ||
                endDatePicker.getValue() == null ||
                endTimePicker.getText() == null || endTimePicker.getText().isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }

    private void closeStage() {
        Stage stage = (Stage) startDatePicker.getScene().getWindow();
        stage.close();
    }

    /*private void ClearEverything() {
        titleShowingTextField.setText("");
        startDatePicker.setValue(null);
        startTimePicker.setText("");
        endDatePicker.setValue(null);
        endTimePicker.setText("");
        durationTextField.setText("");
    }*/

    @FXML public void addShowingPress(ActionEvent actionEvent) {
        addShowing();
    }

    @FXML public void cancelPress(ActionEvent actionEvent) {
        closeStage();
    }
}
