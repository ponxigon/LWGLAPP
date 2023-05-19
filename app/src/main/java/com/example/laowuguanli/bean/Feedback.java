package com.example.laowuguanli.bean;

//服务器反馈信息
public class Feedback {
    private boolean reminder;
    private boolean premise;//前提

    public Feedback(boolean reminder) {
        this.reminder = reminder;
    }

    public Feedback(boolean reminder, boolean premise) {
        this.reminder = reminder;
        this.premise = premise;
    }

    public Feedback() {
    }

    public boolean isReminder() {
        return reminder;
    }

    public Feedback setReminder(boolean reminder) {
        this.reminder = reminder;
        return this;
    }

    public boolean isPremise() {
        return premise;
    }

    public void setPremise(boolean premise) {
        this.premise = premise;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "reminder=" + reminder +
                ", premise=" + premise +
                '}';
    }
}
