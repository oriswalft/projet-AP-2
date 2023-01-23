package com.example;

import java.io.IOException;


import javafx.scene.control.Label;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class PrimaryController {

    @FXML
    private Button connect;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    @FXML
    private Label connexionLabel;

    private final Identification id = new Identification();

    @FXML
    void ss(ActionEvent action) throws IOException{
        String us = username.getText();
        String pw = password.getText();

        if (id.validKey(us, pw)){   // Compare la paire saisie, et passe à l'écran suivant si valide.
            System.out.println("L'authentification a réussi !");

            App.setRoot("secondary");

        } else {                    // En cas d'échec.
            connexionLabel.setText("L'authentification a échoué!");
            connexionLabel.setVisible(true);

            shake(username);
            shake(password);
            password.setStyle("-fx-border-color : #ff0000");
        }
    }

    private void shake(Node node){
        // Animation :
        TranslateTransition tTrans1 = new TranslateTransition();
        
        tTrans1.setDuration(Duration.millis(200));

        tTrans1.setByX(25);
        tTrans1.setCycleCount(4);
        tTrans1.setAutoReverse(true);

        tTrans1.setNode(node);
        tTrans1.play();
    }
}