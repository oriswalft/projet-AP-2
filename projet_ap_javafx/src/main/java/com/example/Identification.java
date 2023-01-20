package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Identification {
    private final String dbURL = "jdbc:mysql://localhost:3306/baseexercice";
    private final String dbUsername = "root";
    private final String dbMDP = "Thomas1003";

    private Connection conn;

    Identification(){
        try {
            conn = DriverManager.getConnection(dbURL, dbUsername, dbMDP);
        } catch (SQLException e ){
            e.printStackTrace();
        }
    }

    public boolean validKey(String username, String password){
        boolean matching = false;  
        try {
            Statement req = conn.createStatement();
            ResultSet res = req.executeQuery("SELECT username,password FROM test_ap");

            while (res.next()){
                String nom = res.getString("username");
                String mdp = res.getString("password");

                if (nom.equals(username)){
                    if (mdp.equals(password.trim())){
                        matching = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matching;
    }
}
