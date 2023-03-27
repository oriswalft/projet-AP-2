package com.example.Controllers.Visiteur;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.example.User;
import com.example.Frais.FicheDeFrais;
import com.example.Frais.FraisForfaitaires;
import com.example.Frais.FraisHForfait;
import com.example.PartieSQL.CoBdd;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class consulterFiche implements Initializable{

    @FXML
    private VBox fichesViewVBox;

    @FXML
    private ListView<String> fichesListView;

    HashMap<String, FicheDeFrais> listeDeFiches = new HashMap<>();
    private final CoBdd CONNEXION_BDD = new CoBdd();

    public consulterFiche(){
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            obtenirFiches();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listeDeFiches.forEach((k,v) ->{
            fichesListView.getItems().add(k);
        });

        fichesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                peuplerAnchorPane(listeDeFiches.get(newValue));
            }
        });
    }


    private void obtenirFiches() throws SQLException{
        ResultSet fichesUser = CONNEXION_BDD.fetchFiches(User.getMATRICULE());

        while (fichesUser.next()){

            FicheDeFrais fiche = new FicheDeFrais();

            fiche.getFraisForfaitaires().add(new FraisForfaitaires("Nuitee", CONNEXION_BDD.getFrais("Nuitee"), fichesUser.getInt("Nuitee")));
            fiche.getFraisForfaitaires().add(new FraisForfaitaires("Kilometre", CONNEXION_BDD.getFrais("Kilometre"),fichesUser.getInt("Kilometre")));
            fiche.getFraisForfaitaires().add(new FraisForfaitaires("Repas_midi", CONNEXION_BDD.getFrais("Repas_midi"), fichesUser.getInt("Repas_midi")));

            ResultSet res = CONNEXION_BDD.fetchHF(fichesUser.getDate("Date").toLocalDate().getMonth().getValue());
            while (res.next()){


                    FraisHForfait frais = new FraisHForfait(res.getString("intitules"), 
                                                            res.getDouble("cout"), 
                                                            res.getInt("id_fk_fraisHF"), 
                                                            CONNEXION_BDD.getDate(res.getInt("id_fk_fraisHF")));

                    fiche.getFraisHForfaits().add(frais);
            }

            listeDeFiches.put(fichesUser.getDate("Date").toLocalDate().getMonth().toString(), fiche);
        }
    }

    private void peuplerAnchorPane(FicheDeFrais frais){
        fichesViewVBox.getChildren().clear();
        fichesViewVBox.getChildren().addAll(
            new Label("Nuitées :" + frais.getFraisForfaitaires().get(0).getQte().get()),
            new Label("Repas du midi :" + frais.getFraisForfaitaires().get(1).getQte().get()),
            new Label("Kilomètres :" + frais.getFraisForfaitaires().get(2).getQte().get())
        );

        for (FraisHForfait i : frais.getFraisHForfaits()){
            System.out.println("Called");
            fichesViewVBox.getChildren().add(
                new Label(i.getIntitule() + ":" + i.getCout() + i.getDate())
            );
        }
    }
}