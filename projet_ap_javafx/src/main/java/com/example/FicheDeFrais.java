package com.example;

import java.util.ArrayList;

public class FicheDeFrais {
    private ArrayList<FraisForfaitaires> fraisForfaitaires = new ArrayList<>();
    private ArrayList<FraisHForfait> fraisHForfaits = new ArrayList<>();


    public ArrayList<FraisForfaitaires> getFraisForfaitaires() {
        return fraisForfaitaires;
    }
    public void setFraisForfaitaires(ArrayList<FraisForfaitaires> fraisForfaitaires) {
        this.fraisForfaitaires = fraisForfaitaires;
    }
    public ArrayList<FraisHForfait> getFraisHForfaits() {
        System.out.println(fraisHForfaits.size());
        return fraisHForfaits;
    }
    public void setFraisHForfaits(ArrayList<FraisHForfait> fraisHForfaits) {
        this.fraisHForfaits = fraisHForfaits;
    }
}
