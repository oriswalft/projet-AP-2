package com.example.Frais;

import com.example.PartieSQL.CoBdd;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class FraisForfaitaires {

    /*
     * Classe qui sert à créer un frais de type Forfaitaire. 
     * Retiens son nom, sa quantité, le montant unitaire ainsi que le total.
     * 
     * Pourquoi Simple<>Property ? 
     * Car l'affichage de ces derniers s'actualise automatiquement, ainsi que leur valeur si un attribut "bind" leur est appliqué. Ce qui est le cas ici.
     */

    private String nom;
    private SimpleIntegerProperty qte;
    private double montantU;
    private SimpleDoubleProperty total;
    private final CoBdd id = new CoBdd();

    public FraisForfaitaires(String nom, Double montantU) {
        // Sers à instancier un nouveau Frais Forfaitaire, dans le cas où on créée une fiche de frais

        this.nom = nom;
        this.qte = new SimpleIntegerProperty(id.getQty(nom));
        this.montantU = montantU;
        this.total = new SimpleDoubleProperty(0);
        this.total.bind(qte.multiply(getMontantU()));
    }

    public FraisForfaitaires(String nom, Double montantU, int qte) {
        // Sers à instancier un nouveau Frais Forfaitaire, dans le cas où on le récupère depuis la base de données.

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
