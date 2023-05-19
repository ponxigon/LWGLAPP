package com.example.laowuguanli.bean;

public class ManageInformation {
    private int id;
    private String name;
    private String city;

    public ManageInformation(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public ManageInformation() {
    }

    public int getId() {
        return id;
    }

    public ManageInformation setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ManageInformation setName(String name) {
        this.name = name;
        return this;
    }

    public String getCity() {
        return city;
    }

    public ManageInformation setCity(String city) {
        this.city = city;
        return this;
    }

    @Override
    public String toString() {
        return "ManageInformation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
