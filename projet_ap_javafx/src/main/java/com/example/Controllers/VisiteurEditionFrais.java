package com.example.Controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.example.FraisForfaitaires;
import com.example.FraisHForfait;
import com.example.PartieSQL.Identification;

import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class VisiteurEditionFrais {
    private Identification conn;
    private ObservableList<FraisForfaitaires> list = FXCollections.observableArrayList();
    
    private final String[] listeMois = {"janvier", "février","mars", "avril", "mai", "juin", "juillet", "aout", "septembre", "octobre", "novembre", "décembre"};

    // TODO: Créer un système pour choisir la fiche à éditer
    //       Créer un mode pour entrer des frais hors forfait
    //       Créer un lien avec les kilomètres depuis la base de données
    

    public VisiteurEditionFrais() {
        this.conn = new Identification();
        
    }

    // Créé le spinner pour les champs de saisie de frais forfaitaire et définit la valeur minimale, maximale et par défaut. Le rend aussi éditable
    private Spinner<Integer> createIntegerSpinner(int def, int min, int max){
        Spinner<Integer> spinner = new Spinner<>();
        SpinnerValueFactory<Integer> spinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min ,max, def );
        
        spinner.setEditable(true);
        spinner.setValueFactory(spinnerFactory);

        return spinner;
    }

    private void remplirList(ResultSet res){
        try {
            // Parcours le ResultSet et ajoute un frais à chaque itération
            while (res.next()){
                String nomFrais = res.getString("name");
                Double coutFrais= res.getDouble("cost");
                list.add(new FraisForfaitaires(nomFrais, coutFrais));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Ajout de l'essence car il n'est pas dans la liste des frais forfaitisés
            list.add(new FraisForfaitaires("Kilométres", conn.fuelCost()));
        }
    }

    public VBox createDisplay(){
        // Se connecte à la BDD et remplis la liste
        ResultSet res = this.conn.getFrais();
        remplirList(res);

        // Récupération de la date :
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);

        // Création de l'affichage :
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
            // Création du label dans la première case :
            grid.add(new Label(i.getNom()), 0, rowCount);

            // Création du spinner dans la deuxième case :
            int max;
            if (i.getNom().equals("Kilométres")){
                max = 99999;
            } else {
                max = 31;
            }

            Spinner<Integer> spin = createIntegerSpinner(0, 0, max);
            spin.valueProperty().addListener((obs, oldValue, newValue) -> {i.setSpinnerValue(newValue);});
            grid.add(spin, 1, rowCount);

            // Création du Label de la troisième case, avec une valeur qui se met à jour automatiquement :
            grid.add(new Label(Double.toString(i.getMontantU())), 2, rowCount);

            Label totLabel = new Label();
            totLabel.textProperty().bind(i.getTotal().asString());
            grid.add(totLabel, 3, rowCount);

            rowCount++;
        }

        // TODO: affichage des lignes pour debug, à styliser en CSS. 
        grid.setGridLinesVisible(true);

        box.getChildren().addAll(new Label("Frais forfaitisés du mois de " + listeMois[month] + " :"), grid, creerHForfait());

        box.setPadding(new Insets(50));
        VBox.setVgrow(box, Priority.ALWAYS);
        box.setAlignment(Pos.TOP_CENTER);

        return box;
    }

    private VBox creerHForfait(){

        // Déclaration des valeurs :
        String[] colName = {"Intitulé","Coût"," "};
        double[] colSize = {0.4,0.4,0.2};
        ObservableList<FraisHForfait> liste = FXCollections.observableArrayList(
            new FraisHForfait("Achat fleur bal",15.89)
        );

        // Création de l'affichage :
        VBox box = new VBox();
        Label titreLabel = new Label("Frais hors forfait :");
        TableView<FraisHForfait> table = new TableView<>();

        // Création des colonnes et de leur propriétés :
        for (int i = 0; i < colName.length; i++){
            TableColumn<FraisHForfait,String> col1 = new TableColumn<>(colName[i]);
            // Taille en fonction de la taille du TableView et impossibilité de redimensionner
            col1.prefWidthProperty().bind(table.widthProperty().multiply(colSize[i]));
            col1.setResizable(false);

            // Ajout de la valeur dans la colonne :
            col1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FraisHForfait,String>,ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<FraisHForfait, String> param) {
                    return new ReadOnlyObjectWrapper<String>(param.getValue().getIntitule());
                }
                
            });

            // Ajout de la colonne au table view :
            table.getColumns().add(col1);
        }

        // TODO: vérifier pertinence de cette ligne
        Property<ObservableList<FraisHForfait>> listeProperty = new SimpleObjectProperty<>(liste);
        table.itemsProperty().bind(listeProperty);

        // TODO : créer système d'affichage et d'édition des frais hors forfait.
        Button but = new Button("Ajouter");
        but.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World !");
            }

        });
            

        box.getChildren().addAll(titreLabel,table,but);
        return box;
    }

}
