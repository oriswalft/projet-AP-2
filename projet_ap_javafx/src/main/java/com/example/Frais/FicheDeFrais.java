package com.example.Frais;

import java.util.ArrayList;

public class FicheDeFrais {

    /*
     * Classe qui instancie une fiche de frais. Sers notamment à afficher les frais dans l'onglet approprié pour les visiteurs.
     */

     // Pourquoi les ArrayList ? Car c'est plus facile à manipuler que les tableaux basiques, et que leur taille n'est pas fixée.
    private ArrayList<FraisForfaitaires> fraisForfaitaires = new ArrayList<>();
    private ArrayList<FraisHForfait> fraisHForfaits = new ArrayList<>();
    private int id; 


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

    public void setId (int id){
        this.id = id;
    }

    public int getId (){
        return this.id;
    }
}
