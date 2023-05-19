package com.example.laowuguanli.bean;

import java.util.Arrays;

public class BoosVacate_Bao {
    private BoosVacate[] boosVacate;

    public BoosVacate_Bao(BoosVacate[] boosVacate) {
        this.boosVacate = boosVacate;
    }

    @Override
    public String toString() {
        return "BoosVacate_Bao{" +
                "boosVacate=" + Arrays.toString(boosVacate) +
                '}';
    }

    public BoosVacate[] getBoosVacate() {
        return boosVacate;
    }

    public void setBoosVacate(BoosVacate[] boosVacate) {
        this.boosVacate = boosVacate;
    }
}
