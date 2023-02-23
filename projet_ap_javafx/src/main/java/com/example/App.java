package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"),1080,600);
        String css = this.getClass().getResource("Stylesheet.css").toExternalForm();
        stage.initStyle(StageStyle.UNDECORATED);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setTitle("Projet AP");
        stage.setResizable(true);
        //stage.setMaximized(true);

        stage.setOnCloseRequest(evt -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("Souhaitez vous vraiment quitter ?");
            alert.showAndWait().filter(r -> r != ButtonType.OK).ifPresent(r->evt.consume());
        });

        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}