package com.example.eind_opdracht;

import com.example.eind_opdracht.controller.LoginController;
import com.example.eind_opdracht.dal.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {

    private Database database;
    @Override
    public void start(Stage stage) throws IOException {

        this.database = new Database();

        if (filesExist()) {
            database.load();
        }
        else {
            database.initializeDefaultUsers();
            database.save();
            database.load();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LoginView.fxml"));
        LoginController controller = new LoginController(database);
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        scene.getStylesheets().add(HelloApplication.class.getResource("style.css").toExternalForm());
        stage.show();
    }

    private boolean filesExist() {
        File showingsFile = new File("showings.dat");
        File ticketsFile = new File("tickets.dat");
        File usersFile = new File("users.dat");

        if (showingsFile.exists() && ticketsFile.exists() && usersFile.exists()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        launch();
    }
}