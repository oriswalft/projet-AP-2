package com.example.Controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.App;
import com.example.FraisForfaitaires;
import com.example.User;
import com.example.PartieSQL.Identification;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

    @FXML
    private VBox slidingMenuVBox;

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
        Identification conn = new Identification();
        ResultSet res = conn.getFrais();

        ObservableList<FraisForfaitaires> list = FXCollections.observableArrayList();
        
        //   LocalDate date = LocalDate.now();

        VBox box = new VBox();
        GridPane grid = new GridPane();

        try {
            while (res.next()){
                String nomFrais = res.getString("name");
                Double coutFrais= res.getDouble("cost");
                list.add(new FraisForfaitaires(nomFrais, coutFrais));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        box.getChildren().addAll(new Label("Frais forfaitisés :"));
        list.forEach(e-> box.getChildren().add(new Label(e.getNom())));
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
