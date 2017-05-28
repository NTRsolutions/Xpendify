package com.samsoft.xpendify.model;

/**
 * Created by Fred on 27-Aug-15.
 */
public class BudgetData {

    int id;
    String budget_amount, budget_category, budget_date;

    public BudgetData() {
    }

    public BudgetData(int id, String budget_amount, String budget_category, String budget_date) {
        this.id = id;
        this.budget_amount = budget_amount;
        this.budget_category = budget_category;
        this.budget_date = budget_date;
    }

    public BudgetData(String budget_amount, String budget_category, String budget_date) {
        this.budget_amount = budget_amount;
        this.budget_category = budget_category;
        this.budget_date = budget_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBudget_amount() {
        return budget_amount;
    }

    public void setBudget_amount(String budget_amount) {
        this.budget_amount = budget_amount;
    }

    public String getBudget_category() {
        return budget_category;
    }

    public void setBudget_category(String budget_category) {
        this.budget_category = budget_category;
    }

    public String getBudget_date() {
        return budget_date;
    }

    public void setBudget_date(String budget_date) {
        this.budget_date = budget_date;
    }

    @Override
    public String toString() {
        return "BudgetData{" +
                "id=" + id +
                ", budget_amount='" + budget_amount + '\'' +
                ", budget_category='" + budget_category + '\'' +
                ", budget_date='" + budget_date + '\'' +
                '}';
    }
}
