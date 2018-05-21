package com.example.joel.myapplication;

import java.io.Serializable;

public class TaxPlanModel implements Serializable {
    private double bonusRev=0;
    private double childDecrease=0;
    private double expenseRev=0;
    private double insurance2Decrease=0;
    private double insuranceDecrease=0;
    private double ltfDecrease=0;
    private double otRev=0;
    private double otherRev=0;
    private double pairDecrease=0;
    private double parentDecrease=0;
    private double privateDecrease=0;
    private double rmfDecrease=0;
    private double salaryRev=0;
    private double serviceRev=0;
    private double socialDecrease=0;

    private double totalRevenue=0;
    private double totalDecrease=0;
    private double totalTax=0;

    private int taxRank=0;
    private double taxRankAmount=0;

    public double getBonusRev() {
        return bonusRev;
    }

    public void setBonusRev(double bonusRev) {
        this.bonusRev = bonusRev;
    }

    public double getChildDecrease() {
        return childDecrease;
    }

    public void setChildDecrease(double childDecrease) {
        this.childDecrease = childDecrease;
    }

    public double getExpenseRev() {
        return expenseRev;
    }

    public void setExpenseRev(double expenseRev) {
        this.expenseRev = expenseRev;
    }

    public double getInsurance2Decrease() {
        return insurance2Decrease;
    }

    public void setInsurance2Decrease(double insurance2Decrease) {
        this.insurance2Decrease = insurance2Decrease;
    }

    public double getInsuranceDecrease() {
        return insuranceDecrease;
    }

    public void setInsuranceDecrease(double insuranceDecrease) {
        this.insuranceDecrease = insuranceDecrease;
    }

    public double getLtfDecrease() {
        return ltfDecrease;
    }

    public void setLtfDecrease(double ltfDecrease) {
        this.ltfDecrease = ltfDecrease;
    }

    public double getOtRev() {
        return otRev;
    }

    public void setOtRev(double otRev) {
        this.otRev = otRev;
    }

    public double getOtherRev() {
        return otherRev;
    }

    public void setOtherRev(double otherRev) {
        this.otherRev = otherRev;
    }

    public double getPairDecrease() {
        return pairDecrease;
    }

    public void setPairDecrease(double pairDecrease) {
        this.pairDecrease = pairDecrease;
    }

    public double getParentDecrease() {
        return parentDecrease;
    }

    public void setParentDecrease(double parentDecrease) {
        this.parentDecrease = parentDecrease;
    }

    public double getPrivateDecrease() {
        return privateDecrease;
    }

    public void setPrivateDecrease(double privateDecrease) {
        this.privateDecrease = privateDecrease;
    }

    public double getRmfDecrease() {
        return rmfDecrease;
    }

    public void setRmfDecrease(double rmfDecrease) {
        this.rmfDecrease = rmfDecrease;
    }

    public double getSalaryRev() {
        return salaryRev;
    }

    public void setSalaryRev(double salaryRev) {
        this.salaryRev = salaryRev;
    }

    public double getServiceRev() {
        return serviceRev;
    }

    public void setServiceRev(double serviceRev) {
        this.serviceRev = serviceRev;
    }

    public double getSocialDecrease() {
        return socialDecrease;
    }

    public void setSocialDecrease(double socialDecrease) {
        this.socialDecrease = socialDecrease;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

    public int getTaxRank() {
        return taxRank;
    }

    public void setTaxRank(int taxRank) {
        this.taxRank = taxRank;
    }

    public double getTaxRankAmount() {
        return taxRankAmount;
    }

    public void setTaxRankAmount(double taxRankAmount) {
        this.taxRankAmount = taxRankAmount;
    }

    public double getTotalDecrease() {
        return totalDecrease;
    }

    public void setTotalDecrease(double totalDecrease) {
        this.totalDecrease = totalDecrease;
    }
}
