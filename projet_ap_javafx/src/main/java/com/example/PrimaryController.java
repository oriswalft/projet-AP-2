package com.example;

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

    @FXML
    void ss(ActionEvent action){
        String dbURL = "jdbc:mysql://localhost:3306/baseexercice";
        String nomUtilisateur = "root";
        String mdp = "Thomas1003";

        try {
            // Connexion en passant les informations spécifiées précédemment
            Connection conn = DriverManager.getConnection(dbURL, nomUtilisateur, mdp);
            // Si on est bien connecté à la DB
            if (conn != null) {
              // On affiche un petit message sur le terminal
              System.out.println("Connexion réussie !");

              ResultSet res = conn.createStatement().executeQuery("SELECT email FROM profil;");

                while (res.next()){
                    System.out.println(res.getString("email"));
                }
            }
          } catch (SQLException ex) {
            // Code de traitement d'erreur
            ex.printStackTrace();
          }

        String us = username.getText();
        String pw = username.getText();
        

    }
}