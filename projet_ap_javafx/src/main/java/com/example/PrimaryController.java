package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    private Button connect;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    private final Identification id = new Identification();

    @FXML
    void ss(ActionEvent action) throws IOException{
        String us = username.getText();
        String pw = password.getText();

        if (id.validKey(us, pw)){
            System.out.println("L'authentification a réussi !");

            App.setRoot("secondary");
        } else {
            System.out.println("L'authentification a échoué! Veuillez réessayer.");
        }
    }
}