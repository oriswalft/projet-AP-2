package com.example;

public class FraisForfaitaires {
    private String nom;
    private int qte;
    private double montantU;
    private double total;

    public FraisForfaitaires(String nom, Double montantU) {
        this.nom = nom;
        this.qte = 0;
        this.montantU = montantU;
        this.total = qte * montantU;
    }

    public String getNom() {
        return nom;
    }

    public int getQte() {
        return qte;
    }

    public double getMontantU() {
        return montantU;
    }

    public double getTotal(){
        return this.total;
    }

    public void setSpinnerValue(Integer newValue) {
        this.qte = newValue;
    }

    public void updateTotal(){
        this.total = this.qte * this.montantU;
        System.out.println(total);
    }

    

    
}
