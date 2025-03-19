package com.example.eind_opdracht.controller;

import com.example.eind_opdracht.dal.Database;
import com.example.eind_opdracht.HelloApplication;
import com.example.eind_opdracht.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class LoginController {

    @FXML private PasswordField loginPasswordField;
    @FXML private TextField loginUsernameTextField;
    @FXML private Button loginBtn;
    @FXML private Label loginLabel;
    @FXML private Label errorLabel;

    private Database database;

    public LoginController(Database database) {
        this.database = database;

    }

    private void openMainMenu(User loggedinuser) throws IOException {
        FXMLLoader Loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/eind_opdracht/MainMenuView.fxml"));

        MainMenuController controller = new MainMenuController(loggedinuser, database);
        Loader.setController(controller);
        Scene scene = new Scene(Loader.load());
        Stage stage = new Stage();
        scene.getStylesheets().add(HelloApplication.class.getResource("style.css").toExternalForm());

        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    @FXML public void loginPress(ActionEvent actionEvent) throws IOException {
        User loginUser = database.checkCombination(loginUsernameTextField.getText(), loginPasswordField.getText());
        if (loginUser != null) {
            openMainMenu(loginUser);
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.close();
        }
        else {
            errorLabel.setText("Invalid username/password combination");
            System.out.println("ERROR: Invalid username/password combination");
        }
    }
}
