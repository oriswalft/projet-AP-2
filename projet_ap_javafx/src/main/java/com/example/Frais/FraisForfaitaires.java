package com.example.Frais;

import com.example.PartieSQL.CoBdd;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class FraisForfaitaires {
    private String nom;
    private SimpleIntegerProperty qte;
    private double montantU;
    private SimpleDoubleProperty total;
    private final CoBdd id = new CoBdd();

    public FraisForfaitaires(String nom, Double montantU) {
        this.nom = nom;
        this.qte = new SimpleIntegerProperty(id.getQty(nom));
        this.montantU = montantU;
        this.total = new SimpleDoubleProperty(0);
        this.total.bind(qte.multiply(getMontantU()));
    }

    public FraisForfaitaires(String nom, Double montantU, int qte) {
        this.nom = nom;
        this.qte = new SimpleIntegerProperty(qte);
        this.montantU = montantU;

        this.total = new SimpleDoubleProperty(0);
        this.total.bind(this.qte.multiply(getMontantU()));
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
        id.setQty(nom, newValue);

    }    
}
