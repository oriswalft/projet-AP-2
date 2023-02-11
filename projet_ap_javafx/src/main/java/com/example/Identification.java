package com.example;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Identification {

    // TODO: changer systématiquement l'ip car adressage DHCP et non statique
    private final String dbURL = "jdbc:mysql://172.16.107.4:3306/projetap";
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
            hashedPw = hashPassword(password.trim());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            Statement req = conn.createStatement();
            // Requête SQL pour récupérer les paires N.U / MDP
            // TODO: Modifier la réponse pour n'avoir que l'ID
            ResultSet res = req.executeQuery(
                    "SELECT matricule,username,mot_de_passe,nom,genre,prenom,id_type_vehicule,id_type_agent FROM projetap.utilisateur join type_agent on id_type_agent = fk_type_agent join type_vehicule on id_type_vehicule = fk_type_vehicule;");

            // Parcours chaque couple et vérifie s'il y a un match
            while (res.next()) {
                String nom = res.getString("username");
                String mdp = res.getString("mot_de_passe");

                if (nom.equals(username)) {
                    if (mdp.equals(hashedPw)) {
                        matching = true;
                        user = new User(res);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matching;
    }

    public String fetchName(){
        try {
            Statement req = conn.createStatement();
            ResultSet res = req.executeQuery("SELECT nom, prenom FROM projetap.utilisateur");

            return res.getString("nom") + " " + res.getString("prenom");
        } catch (Exception e ){
            e.printStackTrace();
        }

        return null;
    }

    private String hashPassword(String pw) throws NoSuchAlgorithmException {
        String res = "";

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest = md.digest(pw.getBytes(StandardCharsets.UTF_8));

        res = convertToHex(messageDigest);
        return res;
    }

    private String convertToHex(final byte[] messageDigest) {
        BigInteger bigint = new BigInteger(1, messageDigest);
        String hexText = bigint.toString(16);
        while (hexText.length() < 32) {
            hexText = "0".concat(hexText);
        }
        return hexText;
    }

    public User getUser() {
        return this.user;
    }
}
