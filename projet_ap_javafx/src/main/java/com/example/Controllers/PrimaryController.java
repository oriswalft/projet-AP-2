package com.example.Controllers;

import java.io.IOException;

import com.example.App;
import com.example.PartieSQL.CoBdd;

import javafx.scene.control.Label;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
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

    @FXML
    private Button closeBtn;

    @FXML
    private HBox usernameHBox;

    @FXML
    private HBox passwordHBox;

    // Connexion à la base de donnée 
    private final CoBdd id = new CoBdd();

    @FXML
    void ss(ActionEvent action) throws IOException{
        login();
    }

    @FXML
    void closeApp (ActionEvent action){
        System.exit(0);
    }

    @FXML   // Récupère les entrées clavier dans le champ de mot de passe et tente de se connecter si "entrée"
    void enterKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                login();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void login() throws IOException{

        String us = username.getText();
        String pw = password.getText();

        if (id.validKey(us, pw)){   // Compare la paire saisie, et passe à l'écran suivant si valide.
            System.out.println("L'authentification a réussi !");

            FXMLLoader loader = new FXMLLoader(App.class.getResource("secondary.fxml"));
            try {   // Charge la classe du second controlleur et met à jour son contenu
                Parent root = loader.load();
                SecondaryController secondaryController = loader.getController();
                secondaryController.changeInfos();
                connect.getScene().setRoot(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
            

        } else {                    // En cas d'échec.
            connexionLabel.setText("L'authentification a échoué!");
            connexionLabel.setVisible(true);

            shake(usernameHBox);
            shake(passwordHBox);
            
            passwordHBox.setStyle("-fx-border-color : #ff0000; -fx-border-radius : 50; -fx-background-radius:50; -fx-background-color: white;");
        }
    }

    private void shake(Node node){
        // Animation :
        TranslateTransition tTrans1 = new TranslateTransition();
        
        tTrans1.setDuration(Duration.millis(200));

        tTrans1.setFromX(0);
        tTrans1.setToX(25);

        tTrans1.setCycleCount(4);
        tTrans1.setAutoReverse(true);

        tTrans1.setNode(node);
        tTrans1.play();
    }
}