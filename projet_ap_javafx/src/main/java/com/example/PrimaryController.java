package com.example;

import java.io.IOException;


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

        if (id.validKey(us, pw)){   // Compare la paire saisie, et passe à l'écran suivant si valide.
            System.out.println("L'authentification a réussi !");

            App.setRoot("secondary");
        } else {                    // En cas d'échec. Peut mettre les zones de saisies en rouge, ou afficher un message.
            System.out.println("L'authentification a échoué! Veuillez réessayer.");
        }
    }
}