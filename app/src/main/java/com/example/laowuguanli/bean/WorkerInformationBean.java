package com.example.laowuguanli.bean;


//工人资料
public class WorkerInformationBean {
    private Integer id;
    private String name;
    private String sex;
    //身份证
    private String identitycard;
    private String companyfront;
    private String companyrear;
    private String operatingpost;
    private String bankid;

    public WorkerInformationBean(Integer id, String name, String age, String identitycard,
                                 String companyfront, String companyrear, String operatingpost, String bankid) {
        this.id = id;
        this.name = name;
        this.sex = age;
        this.identitycard = identitycard;
        this.companyfront = companyfront;
        this.companyrear = companyrear;
        this.operatingpost = operatingpost;
        this.bankid = bankid;
    }

    public WorkerInformationBean() {
    }

    @Override
    public String toString() {
        return "WorkerInformationBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", identitycard='" + identitycard + '\'' +
                ", companyfront='" + companyfront + '\'' +
                ", companyrear='" + companyrear + '\'' +
                ", operatingpost='" + operatingpost + '\'' +
                ", bankid='" + bankid + '\'' +
                '}';
    }

    public String getSex() {
        return sex;
    }

    public WorkerInformationBean setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public WorkerInformationBean setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public WorkerInformationBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getIdentitycard() {
        return identitycard;
    }

    public WorkerInformationBean setIdentitycard(String identitycard) {
        this.identitycard = identitycard;
        return this;
    }

    public String getCompanyfront() {
        return companyfront;
    }

    public WorkerInformationBean setCompanyfront(String companyfront) {
        this.companyfront = companyfront;
        return this;
    }

    public String getCompanyrear() {
        return companyrear;
    }

    public WorkerInformationBean setCompanyrear(String companyrear) {
        this.companyrear = companyrear;
        return this;
    }

    public String getOperatingpost() {
        return operatingpost;
    }

    public WorkerInformationBean setOperatingpost(String operatingpost) {
        this.operatingpost = operatingpost;
        return this;
    }

    public String getBankid() {
        return bankid;
    }

    public WorkerInformationBean setBankid(String bankid) {
        this.bankid = bankid;
        return this;
    }
}
