package com.example.Controllers.Visiteur;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import org.kordamp.ikonli.javafx.FontIcon;

import com.example.Frais.FraisForfaitaires;
import com.example.Frais.FraisHForfait;
import com.example.PartieSQL.CoBdd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class editerFiche {
    private final CoBdd id = new CoBdd();
    private ObservableList<FraisHForfait> hfListe = FXCollections.observableArrayList();
    private ObservableList<FraisForfaitaires> ffListe = FXCollections.observableArrayList();

    @FXML
    private Label titleLabel, coutLabel, intituleLabel, kmLabel, midiLabel, nuiteeLabel, dateLabel, nuiteeCoutLabel, midiCoutLabel, kmCoutLabel, totalFraisLabel;

    @FXML
    private GridPane HFGridPane;

    @FXML
    private Button ajoutButton;

    @FXML
    private Spinner<Integer> kmSpinner, midiSpinner, nuiteeSpinner;

    @FXML
    void showNewDialog(ActionEvent event) {
        // Création d'un nouvel objet de frais :
        FraisHForfait frais = new FraisHForfait("", 0);

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
        FraisForfaitaires midiFrais = new FraisForfaitaires("Repas_midi", id.getFrais("midi"));
        FraisForfaitaires kilometresFrais = new FraisForfaitaires("Kilometre", id.fuelCost());
        ffListe.addAll(nuiteeFrais, midiFrais, kilometresFrais);



        // Bind des propriétés au label des total
        kmLabel.textProperty().bind(kilometresFrais.getTotal().asString());
        kmCoutLabel.setText(Double.toString(kilometresFrais.getMontantU()));
        midiLabel.textProperty().bind(midiFrais.getTotal().asString());
        midiCoutLabel.setText(Double.toString(midiFrais.getMontantU()));
        nuiteeLabel.textProperty().bind(nuiteeFrais.getTotal().asString());
        nuiteeCoutLabel.setText(Double.toString(nuiteeFrais.getMontantU()));

        // Ajout des value factory des spinner
        updateSpinner(kmSpinner, kilometresFrais,9999);
        updateSpinner(nuiteeSpinner, nuiteeFrais,31);
        updateSpinner(midiSpinner, midiFrais,31);

        try {
            ResultSet res = id.fetchHF();
            while (res.next()){


                FraisHForfait frais = new FraisHForfait(res.getString("intitules"), res.getDouble("cout"), res.getInt("id_fk_fraisHF"), id.getDate(res.getInt("id_fk_fraisHF")));

                createHFRow(frais);
                hfListe.add(frais);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        double totalFrais = 0;
        for (FraisHForfait f : hfListe){
            totalFrais += f.getCout();
        }

        for (FraisForfaitaires f : ffListe){
            totalFrais+= f.getTotal().get();
        }
        totalFraisLabel.setText("Total des frais engagés ce mois-ci : " + totalFrais + "€.");
    }

    private void updateSpinner(Spinner<Integer> spin, FraisForfaitaires frais, int max){
        spin.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(0,max,frais.getQte().intValue()));
        spin.valueProperty().addListener((obs, oldValue, newValue) -> {
            frais.setSpinnerValue(newValue);
        });
        spin.setEditable(true);
    }  

    private void updateHFGrid(){
        // Remise à zéro :
        HFGridPane.getChildren().clear();

        // Rajout de la première ligne:
        HFGridPane.addRow(0, intituleLabel, coutLabel,dateLabel, ajoutButton);

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

        // La date
        DatePicker datePicker = new DatePicker();

        if (frais.getDate() != null){
            datePicker.setValue(frais.getDate());
        }
        
        datePicker.setOnAction(e -> {
            LocalDate date = datePicker.getValue();

            if (date.getMonthValue() != LocalDate.now().getMonthValue() || date.getYear() != LocalDate.now().getYear()){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setContentText("La date saisie est différente du mois en cours. Veuillez choisir une date valide et recommencez!");
                alert.show();
                e.consume();
            } else {
                frais.setDate(datePicker.getValue());
            }
        });


        // Création du bouton pour supprimer la ligne et mettre à jour l'affichage :
        Button removeBtn = createDeleteButton(frais);

        // Ajout des éléments à la grille. 
        HFGridPane.addRow(nombreLigne, intituleTextFiled,coutTextField,datePicker,removeBtn);
    }
}