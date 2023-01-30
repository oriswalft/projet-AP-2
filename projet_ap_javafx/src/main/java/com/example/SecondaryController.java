package com.example;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SecondaryController {

    @FXML
    private Button btnDeco;

    @FXML
    private Button btnHome;

    @FXML
    private Label username;

    @FXML
    private HBox spacer;

    @FXML
    private Label nodeNameLabel;


    public void changeInfos() {
        HBox.setHgrow(spacer, Priority.ALWAYS);
        nodeNameLabel.setText("Accueil");

        String type_agent = (User.getTYPE_AGENT() == 1) ? "Visiteur" : "Comptable";
        String sexe = (User.getGENRE() == 1) ? "Mme. " : "M. ";
        username.setText(sexe + User.getNOM() + " " + User.getPRENOM() + " | " + type_agent);
    }

    @FXML
    void deco() throws IOException{
        User.deco();
        App.setRoot("primary");
    }
}
