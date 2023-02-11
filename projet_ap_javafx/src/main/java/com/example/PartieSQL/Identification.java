package com.example.PartieSQL;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.User;

public class Identification {

    // TODO: changer systématiquement l'ip car adressage DHCP et non statique
    private final String dbURL = "jdbc:mysql://192.168.1.65:3306/projetap";
    private final String dbUsername = "gsb";
    private final String dbMDP = "";
    private User user;

    private Connection conn;

    public Identification() {
        // Essaye de se connecter à la base de donnée
        try {
            conn = DriverManager.getConnection(dbURL, dbUsername, dbMDP);
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
                    System.out.println("ici");
                    return false;
                } else {

                    // TODO: requête avec INNER JOIN à modifier, imprécis
                    ResultSet user_info = req.executeQuery(
                        "SELECT DISTINCT matricule, genre, nom, prenom, id_type_vehicule, id_type_agent FROM utilisateur INNER JOIN type_vehicule ON id_type_vehicule = fk_type_vehicule INNER JOIN type_agent ON id_type_agent = fk_type_agent WHERE matricule = '" + res.getString("matricule") +  "';"
                    );

                    @SuppressWarnings("unused") // TODO: Fix ça
                    User usr = new User(user_info);

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

    public User getUser() {
        return this.user;
    }
}
