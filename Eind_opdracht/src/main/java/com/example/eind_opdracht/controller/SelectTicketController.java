package com.example.eind_opdracht.controller;

import com.example.eind_opdracht.HelloApplication;
import com.example.eind_opdracht.model.Place;
import com.example.eind_opdracht.model.Showing;
import com.example.eind_opdracht.model.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SelectTicketController implements Initializable {

    @FXML private GridPane seatsGridPane;
    @FXML private Label selectedShowingLabel;
    @FXML private VBox selectedSeatsVbox;
    @FXML private Button sellTicketBtn;
    @FXML private TextField customerNameTextField;

    private List<Place> selectedPlaces;
    private static final int ROWS = 6;
    private static final int COLUMNS = 12;
    private Showing selectedShowing;
    private Ticket ticket;

    public SelectTicketController(Showing selectedshowing) {
        this.selectedShowing = selectedshowing;
    }

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedPlaces = new ArrayList<>();

        selectedShowingLabel.setText("Selected showing: " + selectedShowing.getStartDateTime() + " " + selectedShowing.getTitle());
        fillSeats();
    }

    public Showing getNewShowing() {
        return selectedShowing;
    }

    public Ticket getTicket() {
        return ticket;
    }

    private void sellTicket() {
        try {
            if(customerNameTextField.getText() == null || customerNameTextField.getText().isEmpty()){
                throw new Exception();
            }
            String customerName = customerNameTextField.getText();
            LocalDateTime now = LocalDateTime.now();

            for (Place place : selectedPlaces) {
                selectedShowing.addTakenSeat(place);
            }

            ticket = new Ticket(now, customerName, selectedShowing, selectedPlaces);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void refresh() {
        selectedSeatsVbox.getChildren().clear();
        int numberOfSeats = selectedPlaces.size();

        if (numberOfSeats > 0) {
            for (Place place : selectedPlaces) {
                Label label = new Label();
                label.setText("Row " + place.getRow() + " / Seat " + place.getSeatNumber());
                selectedSeatsVbox.getChildren().add(label);
            }
            sellTicketBtn.setDisable(false);
            sellTicketBtn.setText("Sell " +numberOfSeats + " tickets");
            sellTicketBtn.setId("BlueBack");
        }
        else {
            sellTicketBtn.setDisable(true);
            sellTicketBtn.setText("Sell");
            sellTicketBtn.setId("");
        }
    }

    private void fillSeats() {
        seatsGridPane.getChildren().clear();

        for (int row = 1; row <= ROWS; row++) {
            for (int column = 1; column <= COLUMNS; column++) {

                Place place = new Place(row, column);
                ToggleButton seatButton = new ToggleButton(String.valueOf(column));
                seatButton.setMinSize(25, 25);
                /*seatButton.setMaxSize(20, 20);*/

                if (selectedShowing.isSeatTaken(place)) {
                    seatButton.setId("RedBack");
                    seatButton.setDisable(true);
                } else {
                    seatButton.setId("");

                    seatButton.setOnAction(event -> {
                        if (seatButton.isSelected()) {
                            seatButton.setId("GreenBack");
                            selectedPlaces.add(place);
                        } else {
                            seatButton.setId("");
                            selectedPlaces.remove(place);
                        }
                        refresh();
                    });
                }

                seatsGridPane.add(seatButton, column - 1, row - 1);
            }
        }
    }

    private void closeStage() {
        Stage stage = (Stage) sellTicketBtn.getScene().getWindow();
        stage.close();
    }

    @FXML public void cancelPress(ActionEvent actionEvent) {
        closeStage();
    }

    @FXML public void sellTicketPress(ActionEvent actionEvent) throws IOException {
        try {
            if (selectedShowing.getSixteenPlus()) {
                if(customerNameTextField.getText() == null || customerNameTextField.getText().isEmpty()){
                    throw new Exception();
                }
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ConfirmationDialog.fxml"));
                ConfirmationController controller = new ConfirmationController(new Ticket(customerNameTextField.getText(), selectedShowing, selectedPlaces));
                fxmlLoader.setController(controller);
                Scene scene = new Scene(fxmlLoader.load());
                Stage dialog = new Stage();
                scene.getStylesheets().add(HelloApplication.class.getResource("style.css").toExternalForm());
                dialog.setScene(scene);
                dialog.setTitle("Confirmation Dialog");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.showAndWait();

                Boolean sixteenPlus = controller.getConfirmation();

                if (sixteenPlus != null && sixteenPlus) {
                    sellTicket();
                    closeStage();
                } else {
                    System.out.println("User canceled or is not 16+");
                }
            } else {
                sellTicket();
                closeStage();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
