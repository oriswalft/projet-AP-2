package com.example;

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
        username.setText("M. " + User.getNOM() + " " + User.getPRENOM());
    }
}
