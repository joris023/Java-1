package com.example.eind_opdracht.controller;

import com.example.eind_opdracht.dal.Database;
import com.example.eind_opdracht.model.User;
import com.example.eind_opdracht.model.Enums.Role;
import com.example.eind_opdracht.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML private Button sellTicketsBtn;
    @FXML private Button manageShowingBtn;
    @FXML private Button salesHistoryBtn;
    @FXML private VBox layout;

    private User user;
    private Database database;

    public MainMenuController(User user, Database database) {
        this.user = user;
        this.database = database;
    }

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        loadScene("WelcomeView.fxml", new WelcomeController(user));
        ShowButtons();
    }

    private void ShowButtons() {
        if(user.getRole() == Role.EMPLOYEE | user.getRole() == Role.MANAGER) {
            manageShowingBtn.setDisable(false);
            salesHistoryBtn.setDisable(false);
            sellTicketsBtn.setDisable(false);
        }
    }

    public void loadScene(String name, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(name));
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
            if (layout.getChildren().size() > 1)
                layout.getChildren().remove(1);
            layout.getChildren().add(scene.getRoot());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR: Loading scene failed");
        }
    }

    @FXML public void openViewSalesHistoryPress(ActionEvent actionEvent) {
        loadScene("SalesHistoryView.fxml", new ViewSalesHistoryController(database));
        manageShowingBtn.setId("");
        sellTicketsBtn.setId("");
        salesHistoryBtn.setId("ActiveView");
    }

    @FXML public void openSellTicketPress(ActionEvent actionEvent) {
        loadScene("SellTicketView.fxml", new SellTicketController(database));
        manageShowingBtn.setId("");
        sellTicketsBtn.setId("ActiveView");
        salesHistoryBtn.setId("");
    }

    @FXML public void openManageShowingPress(ActionEvent actionEvent) {
        loadScene("ManageShowingView.fxml", new ManageShowingController(database));
        manageShowingBtn.setId("ActiveView");
        sellTicketsBtn.setId("");
        salesHistoryBtn.setId("");
    }
}