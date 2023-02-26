package com.example;

public class FraisHForfait {
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
    }

    public double getCout() {
        return cout;
    }

    public void setCout(String cout) {
        try {
            this.cout = Double.parseDouble(cout);
        } catch (Exception e ){
            // Empty
        }
    }
}
