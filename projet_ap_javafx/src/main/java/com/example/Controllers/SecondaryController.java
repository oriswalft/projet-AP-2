package com.example.Controllers;

import java.io.IOException;

import com.example.App;
import com.example.FraisForfaitaires;
import com.example.User;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

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

    public void changeInfos() {
        String type_agent_str = (User.getTYPE_AGENT() == 1) ? "Visiteur" : "Comptable";
        String sexe = (User.getGENRE() == 1) ? "Mme. " : "M. ";
        username.setText(sexe + User.getNOM() + " " + User.getPRENOM() );
        type_agent.setText(type_agent_str);
    }

    private Spinner<Integer> createIntegerSpinner(int def, int min, int max){
        Spinner<Integer> spinner = new Spinner<>();
        SpinnerValueFactory<Integer> nuiteeSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min ,max, def );
        
        spinner.setEditable(true);
        spinner.setValueFactory(nuiteeSpinnerFactory);

        return spinner;
    }

    @FXML
    void deco() throws IOException{
        User.deco();
        App.setRoot("primary");
    }

    @FXML
    void goToEdit(ActionEvent event) {
        ObservableList<FraisForfaitaires> list = FXCollections.observableArrayList( 
            new FraisForfaitaires("Nuitées", 1), 
            new FraisForfaitaires("Repas midi", 15),
            new FraisForfaitaires("Kilomètres", 0));
        
        //   LocalDate date = LocalDate.now();

        VBox box = new VBox();

        // Création du tableau de frais forfaitaires
        TableView<FraisForfaitaires> fraisForfaitaires =  new TableView<>();

        // Création des colonnes du tableau 
        TableColumn<FraisForfaitaires, String> nomFrais = new TableColumn<>("Frais forfaitaires");
        TableColumn<FraisForfaitaires, Spinner<Integer>> qteFrais = new TableColumn<>("Quantité");
        TableColumn<FraisForfaitaires, Double> montantUnitaire = new TableColumn<>("Montant unitaire");

        // TODO: Update total pour qu'il match la valeur de la classe
        TableColumn<FraisForfaitaires, Double> total = new TableColumn<>("Total");

        // Création des ValueFactory
        nomFrais.setCellValueFactory(new PropertyValueFactory<FraisForfaitaires, String>("nom"));

        qteFrais.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FraisForfaitaires,Spinner<Integer>>,ObservableValue<Spinner<Integer>>>() {

            @Override
            public ObservableValue<Spinner<Integer>> call(CellDataFeatures<FraisForfaitaires, Spinner<Integer>> arg0) {
                
                FraisForfaitaires frais = arg0.getValue();
                Spinner<Integer> spin;

                if (arg0.getValue().getNom().equals("Kilomètres")) {
                    spin = createIntegerSpinner(frais.getQte(), 0, 999999);
                } else {
                    spin = createIntegerSpinner(frais.getQte(), 0, 31);
                }
               // TODO: fix le beug de la boucle infinie  

                spin.valueProperty().addListener((obs,oldValue, newValue) -> { frais.setSpinnerValue(newValue); frais.updateTotal(); fraisForfaitaires.refresh();});

                return new SimpleObjectProperty<Spinner<Integer>>(spin);
            }
            
        });
        montantUnitaire.setCellValueFactory(new PropertyValueFactory<FraisForfaitaires, Double>("montantU"));
        total.setCellValueFactory(new PropertyValueFactory<FraisForfaitaires, Double>("total"));

        fraisForfaitaires.setMaxWidth(800);

        fraisForfaitaires.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        fraisForfaitaires.setItems(list);

        fraisForfaitaires.setFixedCellSize(25);
        fraisForfaitaires.prefHeightProperty().bind(Bindings.size(fraisForfaitaires.getItems()).multiply(fraisForfaitaires.getFixedCellSize()).add(50));

        fraisForfaitaires.getColumns().add(nomFrais);
        fraisForfaitaires.getColumns().add(qteFrais);
        fraisForfaitaires.getColumns().add(montantUnitaire);
        fraisForfaitaires.getColumns().add(total);

        box.getChildren().addAll(new Label("Frais forfaitisés :"), fraisForfaitaires);
        box.setPadding(new Insets(50));
        box.setAlignment(Pos.TOP_CENTER);

        screenVBox.getChildren().setAll(box);
    }

    @FXML
    void goToHome(ActionEvent event){
        HBox buttonAlign = new HBox(editButton, readButton);
        HBox.setMargin(buttonAlign, new Insets(0,0,10,10));

        screenVBox.getChildren().setAll(new Label(" Vos options :"), buttonAlign);
    }

    @FXML 
    void goToRead(ActionEvent event){
        screenVBox.getChildren().setAll(new VBox());

    }
}
