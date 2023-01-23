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
    private final String dbURL = "jdbc:mysql://localhost:3306/baseexercice";
    private final String dbUsername = "root";
    private final String dbMDP = "Thomas1003";
    private User user;

    private Connection conn;

    Identification(){
        // Essaye de se connecter à la base de donnée
        try {
            conn = DriverManager.getConnection(dbURL, dbUsername, dbMDP);
        } catch (SQLException e ){
            e.printStackTrace();
        }
    }

    // Fonction qui vérifie que la paire nom d'utilisateur/ mot de passe est valide.
    public boolean validKey(String username, String password){
        boolean matching = false; 
        String hashedPw="";
        
        // Récupère le hash du mot de passe saisi, puis l'affecte à la variable hashedPw. Try/catch car il faut gérer les exceptions
        try {
            hashedPw = hashPassword(password.trim());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            Statement req = conn.createStatement();
            // Requête SQL pour récupérer les paires N.U / MDP
            ResultSet res = req.executeQuery("SELECT username,password FROM test_ap");

            // Parcours chaque couple et vérifie s'il y a un match 
            while (res.next()){
                String nom = res.getString("username");
                String mdp = res.getString("password");

                if (nom.equals(username)){
                    if (mdp.equals(hashedPw)){
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

    private String hashPassword(String pw) throws NoSuchAlgorithmException{
        String res = "";

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest =md.digest(pw.getBytes(StandardCharsets.UTF_8));

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
     
     public User getUser(){
        return this.user;
     }
}
