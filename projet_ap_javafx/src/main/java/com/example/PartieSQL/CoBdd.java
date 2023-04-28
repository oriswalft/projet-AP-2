package com.example.PartieSQL;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Calendar;

import com.example.Autre.User;

/*
 * Fichier qui sert à se connecter à la base de données MySQL, localisée sur le PC d'Aymeric.
 * Il faut appeler la fonction connectDb() dans chaque méthode, afin d'attribuer une seule réponse 
 * à une connexion.

 */

public class CoBdd {

    private final String dbURL = "jdbc:mysql://172.16.107.5:3306/projet_ap2";
    private final String dbMDP = "";
    private final int mois = trouverMois(); // Récupère le mois en cours, afin de récupérer la fiche du mois.
    private final int jourDeChangement = 10;
    private int trouverMois() {
        java.util.Date date = new java.util.Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int jour = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);

        if (jour > jourDeChangement) {
            month = month + 1;
        }

        return month;
    }

    private Connection connectDb() {
        // Essaye de se connecter à la base de donnée
        Connection conn;

        try {
            conn = DriverManager.getConnection(dbURL, "gsb", dbMDP);

            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
    * Fonction qui vérifie que la paire nom d'utilisateur/ mot de passe est valide.
    * @param username : Le nom d'utilisateur
    * @param password : Le mot de passe
    */
    public boolean validKey(String username, String password) {
        Connection conn = connectDb();
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
            // Requête SQL pour valider le couple et récupérer le matricule. Renvoie null si
            // aucun couple n'est trouvé
            String query = "SELECT * FROM utilisateur WHERE mot_de_passe = ? AND username = ?;";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, hashedPw);
            statement.setString(2, username);
            ResultSet res = statement.executeQuery();

            if (res.next()) {
                if (res.getString("matricule").equals(null)) {
                    return false;
                } else {

                    ResultSet user_info = req.executeQuery(
                            "SELECT DISTINCT matricule, genre, nom, prenom, id_type_vehicule, id_type_agent FROM utilisateur INNER JOIN type_vehicule ON id_type_vehicule = fk_type_vehicule INNER JOIN type_agent ON id_type_agent = fk_type_agent WHERE matricule = '"
                                    + res.getString("matricule") + "';");

                    User.setUser(user_info);

                    return true;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param frais : l'intitulé du frais à selectionner
     * 
     * @return le cout 
     */
    public double getFrais(String frais) {
        Connection conn = connectDb();
        try {
            String query = "SELECT name, cost FROM frais_forfaitises WHERE name = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, frais);
            ResultSet res = statement.executeQuery();
            double cout = 0;
            if (res.next()) {
                cout = res.getDouble("cost");
            }

            return cout;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    /**
     * 
     * @return le cout
     */

    public double fuelCost() {
        Connection conn = connectDb();
        try {
            String query = "SELECT Cout FROM type_vehicule WHERE id_type_vehicule = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, User.getTYPE_VEHICULE());
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                return res.getDouble("Cout");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * 
     * @param name le nom du frais à récupérer
     * @return le quantité
     */
    public int getQty(String name) {
        Connection conn = connectDb();
        try {
            String query = "SELECT " + name
                    + ", Date FROM fiches_de_frais WHERE fk_utilisateurs = ? AND MONTH(Date) = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, User.getMATRICULE());
            statement.setInt(2, mois);
            ResultSet res = statement.executeQuery();

            if (res.next()) {
                return res.getInt(name);
            } else {
                if (res.getRow() == 0) {
                    Statement req = conn.createStatement();
                    req.execute(
                            "INSERT INTO fiches_de_frais (fk_utilisateurs,qteNuitee,Kilometre,Repas_midi) VALUES (\""
                                    + User.getMATRICULE() + "\", 0,0,0)");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Modifie la quantité du frais énoncé
     * @param name le nom du frais
     * @param qty la quantité
     */
    public void setQty(String name, int qty) {
        Connection conn = connectDb();
        try {
            String query = "UPDATE fiches_de_frais SET " + name
                    + " = ? WHERE fk_utilisateurs = ? AND MONTH(Date) = ? AND DAY(Date) > " + jourDeChangement;
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, qty);
            statement.setString(2, User.getMATRICULE());
            statement.setInt(3, mois);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @return renvoie tous les frais hors forfaits de l'utilisateur connecté.
     * @throws SQLException
     */
    public ResultSet fetchHF() throws SQLException {
        Connection conn = connectDb();
        String query = "SELECT * from frais_hors_forfaits WHERE fk_fraisHF = ? AND MONTH(Date) = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, User.getMATRICULE());
        statement.setInt(2, mois);
        ResultSet res = statement.executeQuery();

        return res;

    }

    /**
     * 
     * @param mois Le mois pour lequel on souhaite obtenir les frais hors forfaits
     * @param matricule le matricule de l'utilisateur pour lequel on souhaite obtenir les frais hors forfaits
     * @return toutes les informations sur les frais 
     * @throws SQLException
     */
    public ResultSet fetchHF(int mois, String matricule) throws SQLException {
        Connection conn = connectDb();
        String query = "SELECT * FROM frais_hors_forfaits WHERE fk_fraisHF = ? AND MONTH(Date) = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, matricule);
        statement.setInt(2, mois);
        ResultSet res = statement.executeQuery();

        return res;

    }

    /**
     * 
     * @param intitule l'intitulé du frais hors forfait à ajouter
     * @param cout le cout du frais à ajouter
     * @return le nombre de clés générées, -1 s'il y a une erreur
     */

    public int addHF(String intitule, double cout) {
        Connection conn = connectDb();
        try {
            String SQL = "INSERT INTO frais_hors_forfaits (id_fraisHF, fk_fraisHF, intitules, cout) VALUES (?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

            statement.setNull(1, 0);
            statement.setString(2, User.getMATRICULE());
            statement.setString(3, intitule);
            statement.setDouble(4, cout);

            statement.addBatch();
            statement.executeBatch();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else
                return -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * 
     * @param intitule
     * @param cout
     * @param key la clé primaire du frais dans la base de données 
     */
    public void updateHF(String intitule, double cout, int key) {
        Connection conn = connectDb();
        System.out.println("[I] Updating...");
        try {
            String query = "UPDATE frais_hors_forfaits SET intitules = ?, cout = ? WHERE id_fraisHF = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, intitule);
            statement.setDouble(2, cout);
            statement.setInt(3, key);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param key la clé primaire du frais à supprimer
     */

    public void deleteHF(int key) {
        Connection conn = connectDb();
        try {
            String query = "DELETE FROM frais_hors_forfaits WHERE id_fraisHF = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, key);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param key la clé primaire du frais
     * @return une date au format lisible par JavaFX
     */
    public LocalDate getDate(int key) {
        Connection conn = connectDb();
        Date dateSql = null;
        try {
            String query = "SELECT Date FROM frais_hors_forfaits WHERE id_fraisHF = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, key);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                dateSql = res.getDate("Date");
            }
            return dateSql.toLocalDate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 
     * @param date La date pour laquelle il faut modifier
     * @param key la clé primaire du frais à modifier
     */ 
    public void saveDate(LocalDate date, int key) {
        Connection conn = connectDb();
        try {
            String sql = "UPDATE frais_hors_forfaits SET date = (?) WHERE id_fraisHF = \" " + key + "\";";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDate(1, java.sql.Date.valueOf(date));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 
     * @param matricule Le matricule de l'utilisateur pour lequel il faut récupérer les frais forfaitisés 
     * @return tous les frais forfaitisés 
     */
    public ResultSet fetchFiches(String matricule) {
        Connection conn = connectDb();
        try {
            String sql = "SELECT * FROM fiches_de_frais WHERE fk_utilisateurs = (?);";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, matricule);
            ResultSet res = statement.executeQuery();

            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 
     * @return Tous les frais qui n'ont pas été vérifiés par un comptable
     */
    public ResultSet fetchAllUnchecked (){
        Connection conn = connectDb();
        try {
            String sql = "SELECT id_fiche_de_frais, matricule,Date,qteNuitee,Kilometre,Repas_midi,verifiee,nom,prenom FROM fiches_de_frais INNER JOIN utilisateur ON matricule = fk_utilisateurs WHERE verifiee = 0;";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet res = statement.executeQuery();

            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 
     * @param key La clé primaire qui sert à identifier la fiche
     * @param value 1 ou 0 pour vrai ou faux 
    */
    public void markFicheAsChecked(int key, int value){
        Connection conn = connectDb();
        try {
            String sql = "UPDATE fiches_de_frais SET verifiee = (?) WHERE id_fiche_de_frais = \" " + key + "\";";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, value);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
