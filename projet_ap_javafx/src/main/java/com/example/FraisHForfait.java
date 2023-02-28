package com.example;

import com.example.PartieSQL.Identification;

public class FraisHForfait {
    private Identification id = new Identification();
    private String intitule;
    private double cout;

    public FraisHForfait(String intitule, double cout){
        this.intitule = intitule;
        this.cout = cout;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
        id.updateHF(intitule, this.cout);
    }

    public double getCout() {
        return cout;
    }

    public void setCout(String cout) {
        try {
            this.cout = Double.parseDouble(cout);
            id.updateHF(intitule, this.cout);
        } catch (Exception e ){
            // Empty
        }
    }
}
