package com.example.eind_opdracht.controller;

import com.example.eind_opdracht.model.Showing;
import com.example.eind_opdracht.dal.Database;
import com.example.eind_opdracht.model.Ticket;
import com.example.eind_opdracht.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SellTicketController implements Initializable {

    @FXML private Label selectedShowingLabel;
    @FXML private TableView tableViewShowings;
    @FXML private Button selectSeatBtn;
    @FXML private TableColumn<Showing, LocalDateTime> startColumn;
    @FXML private TextField searchShowingTextField;

    private Database database;
    private final DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private ObservableList<Showing> showingsList;

    public SellTicketController(Database database) {
        this.database = database;
    }

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Showing> Showings = getShowings();
        showingsList = FXCollections.observableArrayList(Showings);
        tableViewShowings.setItems(showingsList);

        orderList();

        tableViewShowings.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectSeatBtn.setDisable(newValue == null);
            if (newValue != null) {
                Showing selectedShowing = (Showing) tableViewShowings.getSelectionModel().getSelectedItem();
                selectedShowingLabel.setText("Selected: " + selectedShowing.getStartDateTime()+ " " + selectedShowing.getTitle());
            }
            else {
                selectedShowingLabel.setText("No showing selected.");
            }
        });

        searchShowingTextField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.length() > 2) {
                searchShowings(newValue);
            }
            else {
                tableViewShowings.setItems(showingsList);
            }
        });
    }

    private void searchShowings(String searchText) {
        List<Showing> filterdList = new ArrayList<>();


        for (Showing showing : showingsList) {

            //kon ook contains doen maarja
            if (showing.getTitle().toLowerCase().startsWith(searchText.toLowerCase())) {
                filterdList.add(showing);
            }
        }
        ObservableList<Showing> newListFiltered = FXCollections.observableArrayList(filterdList);

        tableViewShowings.setItems(newListFiltered);
    }

    private void orderList() {
        startColumn.setCellFactory(column -> new TableCell<Showing, LocalDateTime>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(formatter));
                }
            }
        });

        tableViewShowings.getSortOrder().add(startColumn);
        startColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableViewShowings.sort();
    }

    private List<Showing> getShowings() {

        List<Showing> List = new ArrayList<>();

        for (Showing show : database.getShowings()) {
            LocalDateTime startDateTime = LocalDateTime.parse(show.getFormattedStartDateTime(), datetimeformatter);
            LocalDateTime now = LocalDateTime.now();

            if(startDateTime.isAfter(now)) {
                List.add(show);
            }
        }
        return List;
    }

    @FXML public void handleClick(javafx.scene.input.MouseEvent mouseEvent) {
        if (!tableViewShowings.isHover()) {
            tableViewShowings.getSelectionModel().clearSelection();
        }
    }

    @FXML public void selectTicketPress(ActionEvent actionEvent) throws IOException {

        int selectedIndex = tableViewShowings.getSelectionModel().getSelectedIndex();
        Showing selectShowing = showingsList.get(selectedIndex);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("SelectTicketDialog.fxml"));
        SelectTicketController controller = new SelectTicketController(selectShowing);
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load());
        Stage dialog = new Stage();
        scene.getStylesheets().add(HelloApplication.class.getResource("style.css").toExternalForm());
        dialog.setScene(scene);
        dialog.setTitle("Select Tickets");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();

        Showing newShowing = controller.getNewShowing();
        Ticket ticket = controller.getTicket();
        int index = database.getShowings().indexOf(selectShowing);
        int indexList = showingsList.indexOf(selectShowing);

        if (newShowing != null && ticket != null) {
            showingsList.set(indexList, newShowing);
            database.getShowings().set(index, newShowing);
            database.getTickets().add(ticket);
            database.save();
        }
    }
}
