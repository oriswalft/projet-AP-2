package com.example;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SecondaryController {

    @FXML
    private Button btnDeco;

    @FXML
    private Button btnHome;

    @FXML
    private Label username;

    public void changeInfos(){
        username.setText("M. " + User.getNOM() + " " + User.getPRENOM());
    }
}
