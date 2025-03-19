package com.example.eind_opdracht.controller;


import com.example.eind_opdracht.dal.Database;
import com.example.eind_opdracht.model.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewSalesHistoryController implements Initializable {

    @FXML private TableView tableViewTickets;

    private Database database;
    private ObservableList<Ticket> ticketList;

    public ViewSalesHistoryController(Database database) {
        this.database = database;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ticketList = FXCollections.observableArrayList(database.getTickets());

        tableViewTickets.setItems(ticketList);
    }
}
