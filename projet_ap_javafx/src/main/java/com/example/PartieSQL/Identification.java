package com.example.PartieSQL;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.example.User;

public class Identification {

    private final String dbURL = "jdbc:mysql://172.16.107.16:3306/projet ap2";
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

    public double getFrais(String frais){
        try {
            Statement req = conn.createStatement();
            ResultSet res = req.executeQuery("SELECT name, cost from frais_forfaitises WHERE name = \"" + frais + "\";");
            double cout = 0;
            if (res.next()){
                cout = res.getDouble("cost");
            }

            return cout;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0.0;
    }



    public double fuelCost(){
        try {
            Statement req = conn.createStatement();
            ResultSet res = req.executeQuery("SELECT Cout FROM type_vehicule WHERE id_type_vehicule = " + User.getTYPE_VEHICULE() + ";");
            
            while (res.next()){
                return res.getDouble("Cout");
            }

        } catch (SQLException e ){
            e.printStackTrace();
        }
        return 0.0;
    }

    public int getQty(String name){
        try {
            Statement req = conn.createStatement();
            ResultSet res = req.executeQuery("SELECT "+ name + " FROM fiches_de_frais WHERE fk_utilisateurs = \"" + User.getMATRICULE() + "\";");
            
            if (res.next()){
                return res.getInt(name);
            }

        } catch (SQLException e ){
            e.printStackTrace();
        }
        return 0;
    }

    public void setQty(String name, int qty){
        try {
            Statement req = conn.createStatement();
            req.executeUpdate("UPDATE fiches_de_frais SET " + name + " = "+ qty + " WHERE fk_utilisateurs = \"" + User.getMATRICULE() + "\" ;" );
        } catch (SQLException e ){
            e.printStackTrace();
        }
    }

    public ResultSet fetchHF() throws SQLException{

        Statement req = conn.createStatement();
        ResultSet res =req.executeQuery("SELECT * from frais_hors_forfaits WHERE fk_fraisHF=\"" + User.getMATRICULE()+"\";" );


        return res;

    }


    public int addHF(String intitule, double cout){
        try {
            String SQL = "INSERT INTO frais_hors_forfaits (id_fk_fraisHF, fk_fraisHF, intitules, cout) VALUES (?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

            statement.setNull(1, 0);
            statement.setString(2, User.getMATRICULE());
            statement.setString(3, intitule);
            statement.setDouble(4, cout);

            statement.addBatch();
            statement.executeBatch();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
            else return -1;

        } catch (SQLException e){
            e.printStackTrace();
        }

        return -1;
    }

    public void updateHF(String intitule, double cout, int key){
        try {
            Statement req = conn.createStatement();
            req.executeUpdate("UPDATE frais_hors_forfaits SET intitules = \""+ intitule + "\", cout = " + cout + " WHERE id_fk_fraisHF = \"" + key + "\";" );
        } catch (SQLException e ){
            e.printStackTrace();
        }
    }

    public void deleteHF(int key){
        try {
            Statement req = conn.createStatement();
            req.executeUpdate("DELETE FROM frais_hors_forfaits WHERE id_fk_fraisHF = \" " + key + "\" ;" );
        } catch (SQLException e ){
            e.printStackTrace();
        }
    }

    // TODO: Réparer la date 
    public void setHFDate(Date date){
        try {
            Statement req = conn.createStatement();
            req.executeUpdate("UPDATE frais_hors_forfaits SET Date = \" " +  date +"\";" );
        } catch (SQLException e ){
            e.printStackTrace();
        }
    }
}
