package com.example.Controllers;

import java.io.IOException;
import com.example.App;
import com.example.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SecondaryController {

    @FXML
    private Button btnDeco;

    @FXML
    private Button btnHome;

    @FXML
    private Label username;
    
    @FXML
    private Button editButton;

    @FXML
    private Button readButton;

    @FXML
    private Label type_agent;

    @FXML
    private VBox screenVBox;

    @FXML
    private VBox slidingMenuVBox;

    public void changeInfos() {
        // Changement des labels
        String type_agent_str = (User.getTYPE_AGENT() == 1) ? "Visiteur" : "Comptable";
        String sexe = (User.getGENRE() == 1) ? "Mme. " : "M. ";
        username.setText(sexe + User.getNOM() + " " + User.getPRENOM() );
        type_agent.setText(type_agent_str);

    }
    

    @FXML
    void deco() throws IOException{
        // Renvoie toutes les valeur de User à null (bloque l'accès) et renvoie à la page de connexion
        User.deco();
        App.setRoot("primary");
    }

    @FXML
    void goToEdit(ActionEvent event) {
        // Créé la VBox qui sert à afficher l'onglet désiré et l'affiche.
        VisiteurEditionFrais display = new VisiteurEditionFrais();
        VBox box = display.createDisplay();
        screenVBox.getChildren().setAll(box);
    }

    @FXML 
    void goToRead(ActionEvent event){
        // Créé la VBox qui sert à afficher l'onglet désiré et l'affiche.
        screenVBox.getChildren().setAll(new VBox());

    }
}
