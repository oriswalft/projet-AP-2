package com.example.Controllers.Visiteur;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.example.Frais.FicheDeFrais;
import com.example.Frais.FraisForfaitaires;
import com.example.Frais.FraisHForfait;
import com.example.PartieSQL.CoBdd;
import com.example.Utils.User;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class consulterFiche implements Initializable {

    /*
     * Controller pour le fichier "consulterFiche.fxml"
     */

    @FXML
    private VBox fichesViewVBox;

    @FXML
    private ListView<String> fichesListView;

    // Pourquoi un HashMap ? Car j'avais besoin du nom de la variable
    HashMap<String, FicheDeFrais> listeDeFiches = new HashMap<>();
    private final CoBdd CONNEXION_BDD = new CoBdd();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Essaye de récupérer les fiches depuis la base de données.
        try {
            obtenirFiches();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Ajoute chaque élément du HashMap à ListView
        listeDeFiches.forEach((k, v) -> {
            fichesListView.getItems().add(k);
        });

        // Vérifie quel élément est sélectionné, puis affiche la fiche du mois
        // correspondant.
        fichesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                peuplerAnchorPane(listeDeFiches.get(newValue));
            }
        });
    }

    private void obtenirFiches() throws SQLException {
        ResultSet fichesUser = CONNEXION_BDD.fetchFiches(User.getMATRICULE());

        while (fichesUser.next()) {

            FicheDeFrais fiche = new FicheDeFrais();

            // Ajoute chaque frais forfaitaire à la ArrayList "fraisForfaitaires" de la
            // classe fiche de frais.
            fiche.getFraisForfaitaires().add(new FraisForfaitaires("qteNuitee", CONNEXION_BDD.getFrais("qteNuitee"),
                    fichesUser.getInt("qteNuitee")));
            fiche.getFraisForfaitaires().add(new FraisForfaitaires("Kilometre", CONNEXION_BDD.getFrais("Kilometre"),
                    fichesUser.getInt("Kilometre")));
            fiche.getFraisForfaitaires().add(new FraisForfaitaires("Repas_midi", CONNEXION_BDD.getFrais("Repas_midi"),
                    fichesUser.getInt("Repas_midi")));

            // Récupère les frais hors forfaits du mois sélectionné
            ResultSet res = CONNEXION_BDD.fetchHF(fichesUser.getDate("Date").toLocalDate().getMonth().getValue());
            while (res.next()) {

                FraisHForfait frais = new FraisHForfait(res.getString("intitules"),
                        res.getDouble("cout"),
                        res.getInt("id_fraisHF"),
                        CONNEXION_BDD.getDate(res.getInt("id_fraisHF")));

                fiche.getFraisHForfaits().add(frais);
            }

            listeDeFiches.put(fichesUser.getDate("Date").toLocalDate().getMonth().toString(), fiche);
        }
    }

    private void peuplerAnchorPane(FicheDeFrais frais) {
        // Ajoute les éléments du mois correspondant à l'AnchorPane.
        fichesViewVBox.getChildren().clear();
        fichesViewVBox.getChildren().addAll(
                new Label("Nuitées :" + frais.getFraisForfaitaires().get(0).getQte().get()),
                new Label("Repas du midi :" + frais.getFraisForfaitaires().get(1).getQte().get()),
                new Label("Kilomètres :" + frais.getFraisForfaitaires().get(2).getQte().get()));

        for (FraisHForfait i : frais.getFraisHForfaits()) {
            fichesViewVBox.getChildren().add(
                    new Label(i.getIntitule() + ":" + i.getCout() + i.getDate()));
        }
    }
}