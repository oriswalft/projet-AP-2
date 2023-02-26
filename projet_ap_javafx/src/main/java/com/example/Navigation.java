package com.example;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Navigation {

    public static Parent getFXML(String fxml) throws IOException {
        return loadFXML(fxml);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        return fxmlLoader.load();
    }
}
