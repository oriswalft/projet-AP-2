package com.example.PartieSQL;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.User;

public class Identification {

    private final String dbURL = "jdbc:mysql://127.0.0.1:3306/projetap";
    private final String dbMDP = "";

    private Connection conn;

    public Identification() {
        // Essaye de se connecter à la base de donnée
        try {
            conn = DriverManager.getConnection(dbURL, "gsb", dbMDP);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Identification(String username){
        try {
            conn = DriverManager.getConnection(dbURL, username, dbMDP);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fonction qui vérifie que la paire nom d'utilisateur/ mot de passe est valide.
    public boolean validKey(String username, String password) {
        boolean matching = false;
        String hashedPw = "";

        // Récupère le hash du mot de passe saisi, puis l'affecte à la variable
        // hashedPw. Try/catch car il faut gérer les exceptions
        try {
            hashedPw = Hachage.hashPassword(password.trim());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            Statement req = conn.createStatement();
            // Requête SQL pour valider le couple et récupérer le matricule. Renvoie null si aucun couple n'est trouvé
            ResultSet res = req.executeQuery(
                    "SELECT DISTINCT matricule FROM projetap.utilisateur WHERE mot_de_passe = '" + hashedPw + "' AND username = '"+ username+ "';");

            if (res.next()){
                if (res.getString("matricule").equals(null)){
                    return false;
                } else {

                    // TODO: requête avec INNER JOIN à modifier, imprécis
                    ResultSet user_info = req.executeQuery(
                        "SELECT DISTINCT matricule, genre, nom, prenom, id_type_vehicule, id_type_agent FROM utilisateur INNER JOIN type_vehicule ON id_type_vehicule = fk_type_vehicule INNER JOIN type_agent ON id_type_agent = fk_type_agent WHERE matricule = '" + res.getString("matricule") +  "';"
                    );

                    User.setUser(user_info);

                    return true;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matching;
    }

    public void endConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getFrais(){
        try {
            Statement req = conn.createStatement();
            ResultSet res = req.executeQuery("SELECT name, cost from frais_forfaitises");

            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public double fuelCost(){
        try {
            Statement req = conn.createStatement();
            ResultSet res = req.executeQuery("SELECT Cout FROM type_vehicule WHERE id_type_vehicule = " + User.getTYPE_AGENT() + ";");
            
            while (res.next()){
                return res.getDouble("Cout");
            }

        } catch (SQLException e ){
            e.printStackTrace();
        }
        return 0.0;
    }
}
