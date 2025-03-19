package com.example.eind_opdracht.dal;

import com.example.eind_opdracht.model.Showing;
import com.example.eind_opdracht.model.Ticket;
import com.example.eind_opdracht.model.User;
import com.example.eind_opdracht.model.Enums.Role;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable {

    private List<Showing> showings;
    private List<Ticket> tickets;
    private List<User> users;

    public Database() {
        showings = new ArrayList<>();
        tickets = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void initializeDefaultUsers() {
        users.add(new User("Mark", "Welkom", Role.MANAGER));
        users.add(new User("Tom", "Welkom", Role.EMPLOYEE));
    }

    public void save() {
        saveToFile("showings.dat", showings);
        saveToFile("tickets.dat", tickets);
        saveToFile("users.dat", users);
    }

    private <T> void saveToFile(String fileName, List<T> list) {
        try (FileOutputStream fos = new FileOutputStream(new File(fileName));
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (T item : list) {
                oos.writeObject(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR: Saving file went wrong");
        }
    }

    public void load() {
        showings.clear();
        tickets.clear();
        users.clear();

        loadFromFile("showings.dat", showings, Showing.class);
        loadFromFile("tickets.dat", tickets, Ticket.class);
        loadFromFile("users.dat", users, User.class);
    }

    private <T> void loadFromFile(String fileName, List<T> list, Class<T> clazz) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(fileName)))) {
            while (true) {
                try {
                    T item = clazz.cast(ois.readObject());
                    list.add(item);
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR: Loading file went wrong");
        }
    }

    public User checkCombination(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public List<Showing> getShowings() {
        return showings;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
}
