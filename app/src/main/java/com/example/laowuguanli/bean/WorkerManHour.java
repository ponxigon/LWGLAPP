package com.example.laowuguanli.bean;

public class WorkerManHour {
    private int id;
    private String name;
    private int monthhour;
    private int monthsalary;
    private int chummage;
    private int advancemoney;
    private int endmoney;

    public WorkerManHour(int id, String name, int monthhour, int monthsalary, int chummage, int advancemoney, int endmoney) {
        this.id = id;
        this.name = name;
        this.monthhour = monthhour;
        this.monthsalary = monthsalary;
        this.chummage = chummage;
        this.advancemoney = advancemoney;
        this.endmoney = endmoney;
    }

    public WorkerManHour() {
    }

    @Override
    public String toString() {
        return "WorkerManHour{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", monthhour=" + monthhour +
                ", monthsalary=" + monthsalary +
                ", chummage=" + chummage +
                ", advancemoney=" + advancemoney +
                ", endmoney=" + endmoney +
                '}';
    }

    public int getId() {
        return id;
    }

    public WorkerManHour setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public WorkerManHour setName(String name) {
        this.name = name;
        return this;
    }

    public int getMonthhour() {
        return monthhour;
    }

    public WorkerManHour setMonthhour(int monthhour) {
        this.monthhour = monthhour;
        return this;
    }

    public int getMonthsalary() {
        return monthsalary;
    }

    public WorkerManHour setMonthsalary(int monthsalary) {
        this.monthsalary = monthsalary;
        return this;
    }

    public int getChummage() {
        return chummage;
    }

    public WorkerManHour setChummage(int chummage) {
        this.chummage = chummage;
        return this;
    }

    public int getAdvancemoney() {
        return advancemoney;
    }

    public WorkerManHour setAdvancemoney(int advancemoney) {
        this.advancemoney = advancemoney;
        return this;
    }

    public int getEndmoney() {
        return endmoney;
    }

    public WorkerManHour setEndmoney(int endmoney) {
        this.endmoney = endmoney;
        return this;
    }
}
