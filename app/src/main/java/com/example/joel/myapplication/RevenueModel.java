package com.example.joel.myapplication;

import java.io.Serializable;
import java.text.DecimalFormat;

public class RevenueModel implements Serializable {
    private double amount;
    private String date;
    private String title;

    public RevenueModel(String title, double amount, String date) {
        this.title = title;
        this.amount = amount;
        this.date = date;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getAmount(boolean withFormat) {
        if (withFormat) {
            return new DecimalFormat("#,##0.00").format(this.amount);
        }
        return this.amount + "";
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
