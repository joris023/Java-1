package com.example.eind_opdracht.controller;

import com.example.eind_opdracht.model.Showing;
import com.example.eind_opdracht.dal.Database;
import com.example.eind_opdracht.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageShowingController implements Initializable {

    @FXML private Button editBtn;
    @FXML private Button deleteBtn;
    @FXML private TableView tableViewShowings;
    @FXML private Label deleteErrorLabel;

    private Database database;
    private ObservableList<Showing> showingsList;

    public ManageShowingController(Database database) {
        this.database = database;
    }


    @Override public void initialize(URL url, ResourceBundle resourceBundle) {

        showingsList = FXCollections.observableArrayList(database.getShowings());
        tableViewShowings.setItems(showingsList);

        tableViewShowings.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            deleteBtn.setDisable(newValue == null);
            editBtn.setDisable(newValue == null);
            deleteErrorLabel.setText("");
        });
    }

    private void DeleteShowing() {
        Showing deletedShowing = showingsList.get(tableViewShowings.getSelectionModel().getSelectedIndex());

        try {
            if(!deletedShowing.getTakenSeats().isEmpty()) {
                deleteErrorLabel.setText("You can't delete showing with sold tickets");
            }
            else {
                showingsList.remove(deletedShowing);
                database.getShowings().remove(deletedShowing);
                database.save();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML public void addShowingPress(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AddShowingDialog.fxml"));
        AddShowingController controller = new AddShowingController();
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load());

        Stage dialog = new Stage();
        scene.getStylesheets().add(HelloApplication.class.getResource("style.css").toExternalForm());
        dialog.setScene(scene);
        dialog.setTitle("Add Showing");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();

        Showing newShowing = controller.getNewShowing();
        if (newShowing != null) {
            showingsList.addAll(newShowing);
            database.getShowings().add(newShowing);
            database.save();
        }
    }

    @FXML public void deleteShowingPress(ActionEvent actionEvent) {
        DeleteShowing();
    }

    @FXML public void editShowingPress(ActionEvent actionEvent) throws IOException {

        Showing selectShowing = showingsList.get(tableViewShowings.getSelectionModel().getSelectedIndex());

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("EditShowingDialog.fxml"));
        EditShowingController controller = new EditShowingController(selectShowing);
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load());

        Stage dialog = new Stage();
        scene.getStylesheets().add(HelloApplication.class.getResource("style.css").toExternalForm());
        dialog.setScene(scene);
        dialog.setTitle("Edit Showing");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();

        Showing newShowing = controller.getNewShowing();
        int index = database.getShowings().indexOf(selectShowing);

        if (newShowing != null) {
            showingsList.set(index, newShowing);
            database.getShowings().set(index, newShowing);
            database.save();
        }
    }

    @FXML public void handleClick(javafx.scene.input.MouseEvent mouseEvent) {
        if (!tableViewShowings.isHover()) {
            tableViewShowings.getSelectionModel().clearSelection();
        }
    }

    @FXML public void exportToCsvPress(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Showings Data");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            saveShowingsToCsv(file);
        }
    }

    private void saveShowingsToCsv(File file) {
        try (FileWriter writer = new FileWriter(file)) {


            for (Showing showing : showingsList) {
                writer.write(
                        showing.getFormattedStartDateTime() + "," +
                                showing.getFormattedEndDateTime() + "," +
                                showing.getTitle() + "," +
                                (showing.getSeats() - showing.getTakenSeats().size()) + "\n"
                );
            }

            System.out.println("Showings exported successfully to " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
