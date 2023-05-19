package com.example.laowuguanli.bean;

import java.util.ArrayList;

public class BoosLookAdvance_Bao {
    private ArrayList<BoosLookAdvance> boosLookAdvance;

    @Override
    public String toString() {
        return "BoosLookAdvance_Bao{" +
                "boosLookAdvance=" + boosLookAdvance +
                '}';
    }

    public ArrayList<BoosLookAdvance> getBoosLookAdvance() {
        return boosLookAdvance;
    }

    public void setBoosLookAdvance(ArrayList<BoosLookAdvance> boosLookAdvance) {
        this.boosLookAdvance = boosLookAdvance;
    }
}
