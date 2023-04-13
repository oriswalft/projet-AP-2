package com.example.Controllers.Comptable;

import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.example.Frais.FraisHForfait;
import com.example.Frais.FicheDeFrais;
import com.example.Frais.FraisForfaitaires;
import com.example.PartieSQL.CoBdd;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class consulterFiches implements Initializable{

    @FXML
    private ListView<String> fichesListView;

    @FXML
    private VBox fichesViewVBox;

    private CoBdd bdd = new CoBdd();

    // Pourquoi un HashMap ? Car j'avais besoin du nom de la variable
    HashMap<String, FicheDeFrais> listeDeFiches = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ResultSet res = bdd.fetchAllUnchecked();
            obtenirFiches(res);

            listeDeFiches.forEach((k, v) -> {
                fichesListView.getItems().add(k);
            });

            fichesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    peuplerAnchorPane(listeDeFiches.get(newValue));
                }
            });
            
        } catch (Exception e) {

        }

        fichesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                peuplerAnchorPane(listeDeFiches.get(newValue));
            }
        });
    }

    private void peuplerAnchorPane(FicheDeFrais frais) {
        // Ajoute les éléments du mois correspondant à l'AnchorPane.

        Button btn = new Button("Marquer comme vérifiée");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                bdd.markFicheAsChecked(frais.getId(), 1);
            }
            
        });
        fichesViewVBox.getChildren().clear();
        fichesViewVBox.getChildren().addAll(
                new Label("Nuitées :" + frais.getFraisForfaitaires().get(0).getQte().get()),
                new Label("Repas du midi :" + frais.getFraisForfaitaires().get(1).getQte().get()),
                new Label("Kilomètres :" + frais.getFraisForfaitaires().get(2).getQte().get()),
                btn
            );

        for (FraisHForfait i : frais.getFraisHForfaits()) {
            fichesViewVBox.getChildren().add(
                    new Label(i.getIntitule() + ":" + i.getCout() + i.getDate()));
        }
    }

    private void obtenirFiches(ResultSet fichesUser){
        try {

        
        while (fichesUser.next()) {

            FicheDeFrais fiche = new FicheDeFrais();

            // Ajoute chaque frais forfaitaire à la ArrayList "fraisForfaitaires" de la
            // classe fiche de frais.
            fiche.getFraisForfaitaires().add(new FraisForfaitaires("qteNuitee", bdd.getFrais("qteNuitee"),
                    fichesUser.getInt("qteNuitee")));
            fiche.getFraisForfaitaires().add(new FraisForfaitaires("Kilometre", bdd.getFrais("Kilometre"),
                    fichesUser.getInt("Kilometre")));
            fiche.getFraisForfaitaires().add(new FraisForfaitaires("Repas_midi", bdd.getFrais("Repas_midi"),
                    fichesUser.getInt("Repas_midi")));

            // Récupère les frais hors forfaits du mois sélectionné
            ResultSet res = bdd.fetchHF(fichesUser.getDate("Date").toLocalDate().getMonth().getValue(), fichesUser.getString("matricule"));
            while (res.next()) {
                FraisHForfait frais = new FraisHForfait(res.getString("intitules"),
                        res.getDouble("cout"),
                        res.getInt("id_fraisHF"),
                        bdd.getDate(res.getInt("id_fraisHF")));

                fiche.getFraisHForfaits().add(frais);
                fiche.setId(fichesUser.getInt("id_fiche_de_frais"));
            }
            listeDeFiches.put(fichesUser.getString("nom") + " " + fichesUser.getString("prenom" ) + " - " + fichesUser.getDate("Date").toLocalDate().getMonth().toString(), fiche);
        }
    } catch (Exception e ){
        e.printStackTrace();
    }
} 

}
