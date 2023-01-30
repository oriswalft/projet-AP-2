package com.example;

import java.io.IOException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

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
    private Button editButton;

    @FXML
    private Label nodeNameLabel;

    @FXML
    private Button readButton;

    @FXML
    private VBox screenVBox;

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

    @FXML
    void goToEdit(ActionEvent event) {
        nodeNameLabel.setText("Edition de la fiche de frais");
        LocalDate date = LocalDate.now();

        VBox box = new VBox();
        Label etat_frais = new Label("Etat de frais engagés : mois de " + date.getMonth());
        Label matricule = new Label("Matricule : " + User.getMATRICULE());
        Label nom = new Label("Nom : " + User.getNOM() + " " + User.getPRENOM());

        box.getChildren().add(etat_frais);
        box.getChildren().add(matricule);
        box.getChildren().add(nom);

        box.setAlignment(Pos.TOP_CENTER);

        screenVBox.getChildren().setAll(box);
    }

    @FXML
    void goToHome(ActionEvent event){
        HBox buttonAlign = new HBox(editButton, readButton);
        HBox.setMargin(buttonAlign, new Insets(0,0,10,10));

        screenVBox.getChildren().setAll(new Label(" Vos options :"), buttonAlign);
        nodeNameLabel.setText("Accueil");
    }

    @FXML 
    void goToRead(ActionEvent event){
        nodeNameLabel.setText("Consulation des fiches de frais précédentes");
        screenVBox.getChildren().setAll(new VBox());

    }
}
