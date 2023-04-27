package com.example.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    /*
     * Classe qui sert à retenir les informations de l'utilisateur.
     * "static" car il ne peut y avoir qu'un utilisateur par session.
     */

    private static String MATRICULE;
    private static int GENRE;
    private static String NOM;
    private static String PRENOM;
    private static int TYPE_VEHICULE;
    private static int TYPE_AGENT;


    /**
     * 
     * @param res contient toutes les infos de l'utilisateur et sers à les mettre à jour
     */
    public static void setUser(ResultSet res) {
        try {
            if (res.next()){
                User.MATRICULE = res.getString("matricule");
                User.GENRE = res.getInt("genre");
                User.NOM = res.getString("nom");
                User.PRENOM = res.getString("prenom");
                User.TYPE_VEHICULE = res.getInt("id_type_vehicule");
                User.TYPE_AGENT = res.getInt("id_type_agent");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getMATRICULE() {
        return MATRICULE;
    }

    public static int getGENRE() {
        return GENRE;
    }

    public static String getNOM() {
        return NOM;
    }

    public static String getPRENOM() {
        return PRENOM;
    }

    public static int getTYPE_VEHICULE() {
        return TYPE_VEHICULE;
    }

    public static int getTYPE_AGENT() {
        return TYPE_AGENT;
    }

    public static void deco() {
        User.MATRICULE = null;
        User.GENRE = 0;
        User.NOM = null;
        User.PRENOM = null;
        User.TYPE_VEHICULE = 0;
        User.TYPE_AGENT = 0;
    }
}
