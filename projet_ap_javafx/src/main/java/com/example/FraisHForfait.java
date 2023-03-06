package com.example;

import com.example.PartieSQL.Identification;

public class FraisHForfait {
    private Identification id = new Identification();
    private String intitule;
    private double cout;
    private int key;

    // Pour la création de nouveaux frais :
    public FraisHForfait(String intitule, double cout){
        this.intitule = intitule;
        this.cout = cout;

        this.key = id.addHF(intitule, cout);

        System.out.println(this.key);
    }

    // Pour retrouver les frais déjà existants :
    public FraisHForfait(String intitule, double cout, int key){
        this.intitule = intitule;
        this.cout = cout;
        this.key = key;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
        id.updateHF(intitule, this.cout, this.key);
    }

    public double getCout() {
        return cout;
    }

    public void setCout(String cout) {
        try {
            this.cout = Double.parseDouble(cout);
            id.updateHF(intitule, this.cout, this.key);
        } catch (Exception e ){
            // Empty
        }
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void delete(){
        id.deleteHF(this.key);
    }


    
}
