package com.example.eind_opdracht.controller;

import com.example.eind_opdracht.model.Showing;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EditShowingController implements Initializable {

    @FXML private DatePicker startDatePicker;
    @FXML private TextField startTimePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private TextField endTimePicker;
    @FXML private TextField titleShowingTextField;
    @FXML private CheckBox sixteenPlusBtn;

    private DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm");
    private DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private Showing selectedShowing;
    private Showing newShowing;

    public EditShowingController(Showing selectedShowing){
        this.selectedShowing = selectedShowing;
    }

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {

        LocalDateTime startDateTime = LocalDateTime.parse(selectedShowing.getFormattedStartDateTime(), datetimeformatter);
        LocalDateTime endDateTime = LocalDateTime.parse(selectedShowing.getFormattedEndDateTime(), datetimeformatter);

        LocalTime startTimeValue = startDateTime.toLocalTime();
        LocalTime endTimeValue = endDateTime.toLocalTime();

        String formattedTime = startTimeValue.format(timeformatter);
        String formattedEnd = endTimeValue.format(timeformatter);

        titleShowingTextField.setText(selectedShowing.getTitle());
        startDatePicker.setValue(startDateTime.toLocalDate());
        startTimePicker.setText(formattedTime);
        endDatePicker.setValue(endDateTime.toLocalDate());
        endTimePicker.setText(formattedEnd);
        sixteenPlusBtn.setSelected(selectedShowing.getSixteenPlus());

        startDatePicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                LocalDate startDate = startDatePicker.getValue();
                if (startDate != null) {
                    endDatePicker.setValue(startDate);
                }
            }
        });
    }

    public Showing getNewShowing() {
        return newShowing;
    }

    private void editShowing() {
        try {
            if (!isFilled()) {
                throw new Exception("ERROR: Vul alle boxen goed in");
            }
            String titleShowingValue = titleShowingTextField.getText();
            LocalDate startDateValue = startDatePicker.getValue();
            LocalTime startTimeValue = LocalTime.parse(startTimePicker.getText(), timeformatter);
            LocalDate endDateValue = endDatePicker.getValue();
            LocalTime endTimeValue = LocalTime.parse(endTimePicker.getText(), timeformatter);

            LocalDateTime startDateTime = LocalDateTime.of(startDateValue, startTimeValue);
            LocalDateTime endDateTime = LocalDateTime.of(endDateValue, endTimeValue);

            Boolean sixteenPlusBtnValue = sixteenPlusBtn.isSelected();

            if (startDateTime.isAfter(endDateTime)) {
                throw new Exception("ERROR: Startdatum kan niet later zijn dan de einddatum.");
            }

            newShowing = new Showing(startDateTime ,endDateTime, titleShowingValue, 72, sixteenPlusBtnValue);

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
        Stage stage = (Stage) titleShowingTextField.getScene().getWindow();
        stage.close();
    }

    @FXML public void editShowingPress(ActionEvent actionEvent) throws IOException {
        editShowing();
    }

    @FXML public void cancelPress(ActionEvent actionEvent) throws IOException {
        closeStage();
    }

}
