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


    public void changeInfos() {
        HBox.setHgrow(spacer, Priority.ALWAYS);

        String type_agent = (User.getTYPE_AGENT() == 1) ? "Visiteur" : "Comptable";
        username.setText("M. " + User.getNOM() + " " + User.getPRENOM() + " | " + type_agent);
    }

    @FXML
    void deco() throws IOException{
        User.deco();
        App.setRoot("primary");
    }
}
