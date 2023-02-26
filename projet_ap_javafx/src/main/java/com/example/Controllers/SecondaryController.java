package com.example.Controllers;

import java.io.IOException;
import com.example.App;
import com.example.Navigation;
import com.example.User;
import com.example.Controllers.Visiteur.editerFiche;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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

    @FXML
    private Pane secPane;
    
    @FXML
    private HBox screen;

    public void changeInfos() throws IOException {
        // Changement des labels
        String type_agent_str = (User.getTYPE_AGENT() == 1) ? "Visiteur" : "Comptable";
        String sexe = (User.getGENRE() == 1) ? "Mme. " : "M. ";
        username.setText(sexe + User.getNOM() + " " + User.getPRENOM() );
        type_agent.setText(type_agent_str);

        // Permet de déplacer la fenêtre, impossible sinon
        screen.setOnMousePressed(pressEvent -> {
            screen.setOnMouseDragged(dragEvent -> {
                screen.getScene().getWindow().setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                screen.getScene().getWindow().setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });

        secPane.getChildren().add(Navigation.getFXML("Visiteur/accueil.fxml"));
    }
    

    @FXML
    void deco() throws IOException{
        // Renvoie toutes les valeur de User à null (bloque l'accès) et renvoie à la page de connexion
        User.deco();
        App.setRoot("primary");
    }

    @FXML
    void goToEdit(ActionEvent event) throws IOException {
        // Créé la VBox qui sert à afficher l'onglet désiré et l'affiche.

        /*
        VisiteurEditionFrais display = new VisiteurEditionFrais();
        VBox box = display.createDisplay();
        screenVBox.getChildren().setAll(box);
         */
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Visiteur/editerFiche.fxml"));
        Parent secPaneRoot = fxmlLoader.load();
        editerFiche editerFiche = fxmlLoader.getController();
        editerFiche.load();
        secPane.getChildren().setAll(secPaneRoot);
    }

    @FXML 
    void goToRead(ActionEvent event) throws IOException{
        // Créé la VBox qui sert à afficher l'onglet désiré et l'affiche.
        secPane.getChildren().setAll(Navigation.getFXML("Visiteur/consulterFiche.fxml"));

    }

    @FXML
    void goToHome(ActionEvent event) throws IOException {
        secPane.getChildren().setAll(Navigation.getFXML("Visiteur/accueil.fxml"));
    }
    @FXML
    void closeApp (ActionEvent action){
        System.exit(0);
    }
}
