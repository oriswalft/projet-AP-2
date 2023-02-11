package com.example;

public class FraisForfaitaires {
    private String nom;
    private int qte;
    private double montantU;
    private double total;

    public FraisForfaitaires(String nom, int qte) {
        this.nom = nom;
        this.qte = qte;
        this.montantU = 80;
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
