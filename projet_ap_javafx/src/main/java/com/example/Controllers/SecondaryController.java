package com.example.Controllers;

import java.io.IOException;
import com.example.App;
import com.example.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    

    @FXML
    void deco() throws IOException{
        User.deco();
        App.setRoot("primary");
    }

    @FXML
    void goToEdit(ActionEvent event) {
        VisiteurEditionFrais display = new VisiteurEditionFrais();
        VBox box = display.createDisplay();
        screenVBox.getChildren().setAll(box);
    }

    @FXML 
    void goToRead(ActionEvent event){
        screenVBox.getChildren().setAll(new VBox());

    }
}
