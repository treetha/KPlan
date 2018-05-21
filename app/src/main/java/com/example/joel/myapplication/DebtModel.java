package com.example.joel.myapplication;

import java.io.Serializable;
import java.text.DecimalFormat;

public class DebtModel implements Serializable {
    private double amount;
    private String bankName;
    private String type;

    public DebtModel(String type, double amount, String bankName) {
        this.type = type;
        this.amount = amount;
        this.bankName = bankName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
