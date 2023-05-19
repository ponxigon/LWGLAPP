package com.example.laowuguanli.bean;

import lombok.Data;

//企业资料
@Data
public class BoosInformation {
    private int id;
    private String name;
    private String company;
    private String city;
    public BoosInformation() {
    }

    public BoosInformation(int id, String name, String company, String city) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.city = city;
    }

    @Override
    public String toString() {
        return "BoosInformation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public String getCity() {
        return city;
    }

    public BoosInformation setCity(String city) {
        this.city = city;
        return this;
    }

    public int getId() {
        return id;
    }

    public BoosInformation setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BoosInformation setName(String name) {
        this.name = name;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public BoosInformation setCompany(String company) {
        this.company = company;
        return this;
    }
}
