package com.example.laowuguanli.bean;

public class UserOtherMessage {
    private String name;
    private String company;
    private String city;
    private String sex;
    private String identitycard;
    private String companyfront;
    private String companyrear;
    private String operatingpost;
    private String bankid;

    public UserOtherMessage() {
    }

    public UserOtherMessage(String name, String company, String city, String age, String identitycard
            , String companyfront, String companyrear, String operatingpost, String bankid) {
        this.name = name;
        this.company = company;
        this.city = city;
        this.sex = age;
        this.identitycard = identitycard;
        this.companyfront = companyfront;
        this.companyrear = companyrear;
        this.operatingpost = operatingpost;
        this.bankid = bankid;
    }

    @Override
    public String toString() {
        return "UserOtherMessage{" +
                "name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", city='" + city + '\'' +
                ", sex='" + sex + '\'' +
                ", identitycard='" + identitycard + '\'' +
                ", companyfront='" + companyfront + '\'' +
                ", companyrear='" + companyrear + '\'' +
                ", operatingpost='" + operatingpost + '\'' +
                ", bankid='" + bankid + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdentitycard() {
        return identitycard;
    }

    public void setIdentitycard(String identitycard) {
        this.identitycard = identitycard;
    }

    public String getCompanyfront() {
        return companyfront;
    }

    public void setCompanyfront(String companyfront) {
        this.companyfront = companyfront;
    }

    public String getCompanyrear() {
        return companyrear;
    }

    public void setCompanyrear(String companyrear) {
        this.companyrear = companyrear;
    }

    public String getOperatingpost() {
        return operatingpost;
    }

    public void setOperatingpost(String operatingpost) {
        this.operatingpost = operatingpost;
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }
}
