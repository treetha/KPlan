package com.example.joel.myapplication;

import java.io.Serializable;
import java.text.DecimalFormat;

public class AssetModel implements Serializable {
    private double amount;
    private String depositType;
    private String type;

    public AssetModel(String type, double amount, String depositType) {
        this.type = type;
        this.amount = amount;
        this.depositType = depositType;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount(boolean withFormat) {
        if (withFormat) {
            return new DecimalFormat("#,##0.00").format(this.amount);
        }
        return this.amount + "";
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDepositType() {
        return this.depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType = depositType;
    }
}
