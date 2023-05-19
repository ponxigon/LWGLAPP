package com.example.laowuguanli.bean;

public class ComplaintEmail {
    private String company;
    private String mailContent;

    @Override
    public String toString() {
        return "ComplaintEmail{" +
                "company='" + company + '\'' +
                ", mailContent='" + mailContent + '\'' +
                '}';
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }
}
