package com.example.Controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.FraisForfaitaires;
import com.example.PartieSQL.Identification;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class VisiteurEditionFrais {
    private Identification conn;
    private ObservableList<FraisForfaitaires> list = FXCollections.observableArrayList();

    // TODO: Créer un système pour choisir la fiche à éditer
    //       Créer un mode pour entrer des frais hors forfait
    //       Créer un lien avec les kilomètres depuis la base de données
    

    public VisiteurEditionFrais() {
        this.conn = new Identification();
        
    }

    private Spinner<Integer> createIntegerSpinner(int def, int min, int max){
        Spinner<Integer> spinner = new Spinner<>();
        SpinnerValueFactory<Integer> nuiteeSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min ,max, def );
        
        spinner.setEditable(true);
        spinner.setValueFactory(nuiteeSpinnerFactory);

        return spinner;
    }

    private void remplirList(ResultSet res){
        try {
            while (res.next()){
                String nomFrais = res.getString("name");
                Double coutFrais= res.getDouble("cost");
                list.add(new FraisForfaitaires(nomFrais, coutFrais));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public VBox createDisplay(){
        ResultSet res = this.conn.getFrais();
        remplirList(res);

        VBox box = new VBox();
        GridPane grid = new GridPane();
        for (int i = 0; i < 4; i++){
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(25);
            col.setHalignment(HPos.CENTER);
            grid.getColumnConstraints().add(col);
        }
        int rowCount = 0;

        for (FraisForfaitaires i : list){
            grid.add(new Label(i.getNom()), 0, rowCount);
            Spinner<Integer> spin = createIntegerSpinner(0, 0, 31);
            spin.valueProperty().addListener((obs, oldValue, newValue) -> {i.setSpinnerValue(newValue);});
            grid.add(spin, 1, rowCount);
            grid.add(new Label(Double.toString(i.getMontantU())), 2, rowCount);

            Label totLabel = new Label();
            totLabel.textProperty().bind(i.getTotal().asString());
            grid.add(totLabel, 3, rowCount);

            rowCount++;
        }

        grid.setGridLinesVisible(true);

        box.getChildren().addAll(new Label("Frais forfaitisés :"), grid);
        box.setPadding(new Insets(50));
        VBox.setVgrow(box, Priority.ALWAYS);
        box.setAlignment(Pos.TOP_CENTER);

        return box;
    }

}
