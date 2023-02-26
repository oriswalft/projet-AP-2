package com.example.Controllers.Visiteur;

import com.example.FraisForfaitaires;
import com.example.PartieSQL.Identification;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class editerFiche {

    private final Identification id = new Identification();

    @FXML
    private GridPane HFGridPane;

    @FXML
    private Button ajoutButton;

    @FXML
    private Label kmLabel;

    @FXML
    private Spinner<Integer> kmSpinner;

    @FXML
    private Label midiLabel;

    @FXML
    private Spinner<Integer> midiSpinner;

    @FXML
    private Label nuiteeLabel;

    @FXML
    private Spinner<Integer> nuiteeSpinner;

    @FXML
    void showNewDialog(ActionEvent event) {
        int nombreLigne = HFGridPane.getRowCount();
        System.out.println(nombreLigne);
        TextField intituleTextFiled = new TextField();
        TextField coutTextField = new TextField();

        intituleTextFiled.setPromptText("Intitulé");
        coutTextField.setPromptText("Coût");

        HFGridPane.add(new TextField("Intitulé"), 0, nombreLigne);
        HFGridPane.add(new TextField("Coût"), 1, nombreLigne);
    }

    public void load(){
        // Création de l'objet du frais
        FraisForfaitaires nuiteeFrais = new FraisForfaitaires("Nuitées", id.getFrais("nuitee"));
        FraisForfaitaires midiFrais = new FraisForfaitaires("Repas du midi", id.getFrais("midi"));
        FraisForfaitaires kilometresFrais = new FraisForfaitaires("Kilomètres", id.fuelCost());

        // Bind des propriétés au label des total
        kmLabel.textProperty().bind(kilometresFrais.getTotal().asString());
        midiLabel.textProperty().bind(midiFrais.getTotal().asString());
        nuiteeLabel.textProperty().bind(nuiteeFrais.getTotal().asString());

        // Ajout des value factory des spinner
        updateSpinner(kmSpinner, kilometresFrais,9999);
        updateSpinner(nuiteeSpinner, nuiteeFrais,31);
        updateSpinner(midiSpinner, midiFrais,31);
    }

    private void updateSpinner(Spinner<Integer> spin, FraisForfaitaires frais, int max){
        spin.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(0,max,0));
        spin.valueProperty().addListener((obs, oldValue, newValue) -> {frais.setSpinnerValue(newValue);});
        spin.setEditable(true);
    }  
}