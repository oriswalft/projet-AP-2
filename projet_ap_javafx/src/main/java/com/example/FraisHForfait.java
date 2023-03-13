package com.example;

import java.time.LocalDate;

import com.example.PartieSQL.CoBdd;

public class FraisHForfait {
    private CoBdd id = new CoBdd();
    private String intitule;
    private double cout;
    private LocalDate date;
    private int key;

    // Pour la création de nouveaux frais :
    public FraisHForfait(String intitule, double cout){
        this.intitule = intitule;
        this.cout = cout;

        this.key = id.addHF(intitule, cout);
    }

    // Pour retrouver les frais déjà existants :
    public FraisHForfait(String intitule, double cout, int key, LocalDate date){
        this.intitule = intitule;
        this.cout = cout;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }


    // TODO : Réparer la date
    public void setDate(LocalDate date) {
        this.date = date;
        id.saveDate(date, this.key);
    }
    
}
