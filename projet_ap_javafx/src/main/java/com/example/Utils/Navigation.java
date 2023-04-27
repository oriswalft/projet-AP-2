package com.example.Utils;

import java.io.IOException;

import com.example.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Navigation {

    /*
     * Classe qui sert à naviguer dans l'application.
     */


     /**
      * 
      * @param fxml Le nom de la fiche vers laquelle naviguer
      * @return un objet qui contient la fiche
      * @throws IOException
      */
    public static Parent getFXML(String fxml) throws IOException {
        return loadFXML(fxml);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        return fxmlLoader.load();
    }
}
