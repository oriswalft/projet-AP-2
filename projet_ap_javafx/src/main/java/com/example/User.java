package com.example;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private static String MATRICULE;
    private static int GENRE;
    private static String NOM;
    private static String PRENOM;
    private static int TYPE_VEHICULE;
    private static int TYPE_AGENT;

    User(){}

    User(ResultSet res){
        try {
            User.MATRICULE = res.getString("matricule");
            User.GENRE = res.getInt("genre");
            User.NOM = res.getString("nom");
            User.PRENOM = res.getString("prenom");
            User.TYPE_VEHICULE = res.getInt("fk_type_vehicule");
            User.TYPE_AGENT = res.getInt("fk_type_agent");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getInstance(){
        return this;
    }
}
