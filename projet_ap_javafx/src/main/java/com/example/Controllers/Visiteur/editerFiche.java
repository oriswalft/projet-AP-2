package com.example.Controllers.Visiteur;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.kordamp.ikonli.javafx.FontIcon;

import com.example.FicheDeFrais;
import com.example.FraisForfaitaires;
import com.example.FraisHForfait;
import com.example.PartieSQL.Identification;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class editerFiche {
    private final Identification id = new Identification();
    private ObservableList<FraisHForfait> hfListe = FXCollections.observableArrayList();
    // Création de la fiche de frais :
    private FicheDeFrais fdf = new FicheDeFrais();

    @FXML
    private Label titleLabel;

    @FXML
    private GridPane HFGridPane;

    @FXML
    private Button ajoutButton;

    @FXML
    private Label coutLabel;

    @FXML
    private Label intituleLabel;

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
        // Création d'un nouvel objet de frais :
        FraisHForfait frais = new FraisHForfait("", 0);
        fdf.getFraisHForfaits().add(frais);

        // Création de la ligne :
        createHFRow(frais);

        // Ajout du nouveau frais créé à la ligne observable.
        hfListe.add(frais);

    }

    public void load(){
        // Mise à jour du label en fonction du mois séléctionné :
        String[] moisList = {"janvier","février","mars","avril","mai","juin","juillet","aout","septembre","octobre","novembre","décembre"};

        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        String mois = moisList[month];

        titleLabel.setText("Edition de la fiche du mois de " + mois +":");

        // Création de l'objet du frais
        FraisForfaitaires nuiteeFrais = new FraisForfaitaires("Nuitee", id.getFrais("nuitee"));
        fdf.getFraisForfaitaires().add(nuiteeFrais);
        FraisForfaitaires midiFrais = new FraisForfaitaires("Repas_midi", id.getFrais("midi"));
        fdf.getFraisForfaitaires().add(midiFrais);
        FraisForfaitaires kilometresFrais = new FraisForfaitaires("Kilometre", id.fuelCost());
        fdf.getFraisForfaitaires().add(kilometresFrais);



        // Bind des propriétés au label des total
        kmLabel.textProperty().bind(kilometresFrais.getTotal().asString());
        midiLabel.textProperty().bind(midiFrais.getTotal().asString());
        nuiteeLabel.textProperty().bind(nuiteeFrais.getTotal().asString());

        // Ajout des value factory des spinner
        updateSpinner(kmSpinner, kilometresFrais,9999);
        updateSpinner(nuiteeSpinner, nuiteeFrais,31);
        updateSpinner(midiSpinner, midiFrais,31);

        try {
            ResultSet res = id.fetchHF();
            while (res.next()){

                FraisHForfait frais = new FraisHForfait(res.getString("intitules"), res.getDouble("cout"), res.getInt("id_fk_fraisHF"));

                createHFRow(frais);
                hfListe.add(frais);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void updateSpinner(Spinner<Integer> spin, FraisForfaitaires frais, int max){
        spin.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(0,max,frais.getQte().intValue()));
        spin.valueProperty().addListener((obs, oldValue, newValue) -> {
            frais.setSpinnerValue(newValue);
            // TODO : Mettre dans setSpinner value
            id.setQty(frais.getNom(), newValue);
        });
        spin.setEditable(true);
    }  

    private void updateHFGrid(){
        // Remise à zéro :
        HFGridPane.getChildren().clear();

        // Rajout de la première ligne:
        HFGridPane.addRow(0, intituleLabel, coutLabel, ajoutButton);

        // Ajout des lignes qui n'ont pas été supprimées :
        hfListe.forEach(e -> {
            createHFRow(e);
        });
    }

    private Button createDeleteButton(FraisHForfait frais){
        // Création du bouton :
        Button button = new Button();
        // Ajout de l'icone en rouge et fond transparent :
        FontIcon icon = new FontIcon("fa-trash");
        icon.setIconColor(Color.RED);
        button.setGraphic(icon);
        button.setStyle("-fx-background-color: transparent;");
        // Ajout de la fonction pour supprimer le bouton lors du clic :
        button.setOnAction(e -> {
            hfListe.remove(frais);
            frais.delete();
            updateHFGrid();
        });

        return button;
    }

    private void createHFRow(FraisHForfait frais){
        // Récupération du nombre de ligne pour permettre d'ajouter à la fin :
        int nombreLigne = HFGridPane.getRowCount();

        // Création des zones de textes et récupération automatique du texte :
        TextField intituleTextFiled = new TextField();
        intituleTextFiled.setOnKeyReleased( e -> frais.setIntitule(intituleTextFiled.getText()));
        intituleTextFiled.setText(frais.getIntitule());
        intituleTextFiled.setPromptText("Intitulé");

        TextField coutTextField = new TextField();
        coutTextField.setOnKeyReleased(e -> frais.setCout(coutTextField.getText()));
        coutTextField.setText(Double.toString(frais.getCout()));
        coutTextField.setPromptText("Coût");

        // TODO: Ajouter date 

        // Création du bouton pour supprimer la ligne et mettre à jour l'affichage :
        Button removeBtn = createDeleteButton(frais);

        // Ajout des éléments à la grille. 
        HFGridPane.addRow(nombreLigne, intituleTextFiled,coutTextField,removeBtn);
    }
}