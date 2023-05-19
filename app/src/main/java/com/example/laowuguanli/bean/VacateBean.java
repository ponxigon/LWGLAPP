package com.example.laowuguanli.bean;

import java.util.Arrays;

public class VacateBean {
    private WorkerLookVacateBean[] beans;

    @Override
    public String toString() {
        return "VacateBean{" +
                "beans=" + Arrays.toString(beans) +
                '}';
    }

    public WorkerLookVacateBean[] getBeans() {
        return beans;
    }

    public void setBeans(WorkerLookVacateBean[] beans) {
        this.beans = beans;
    }
}
