package com.example;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class FraisForfaitaires {
    private String nom;
    private SimpleIntegerProperty qte;
    private double montantU;
    private SimpleDoubleProperty total;

    public FraisForfaitaires(String nom, Double montantU) {
        this.nom = nom;
        this.qte = new SimpleIntegerProperty(0);
        this.montantU = montantU;
        this.total = new SimpleDoubleProperty(0);
        this.total.bind(qte.multiply(getMontantU()));
    }

    public String getNom() {
        return nom;
    }

    public SimpleIntegerProperty getQte() {
        return qte;
    }

    public double getMontantU() {
        return montantU;
    }

    public SimpleDoubleProperty getTotal(){
        return this.total;
    }

    public void setSpinnerValue(Integer newValue) {
        this.qte.set(newValue);
    }    
}
