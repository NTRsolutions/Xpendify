package com.samsoft.xpendify.model;

/**
 * Created by Fred on 28-Aug-15.
 */
public class AccountData {

    int id;
    String balance, customer_bank, account_number, account_type;

    public AccountData() {
    }

    public AccountData(int id, String balance, String customer_bank, String account_number, String account_type) {
        this.id = id;
        this.balance = balance;
        this.customer_bank = customer_bank;
        this.account_number = account_number;
        this.account_type = account_type;
    }

    public AccountData(String balance, String customer_bank, String account_number, String account_type) {
        this.balance = balance;
        this.customer_bank = customer_bank;
        this.account_number = account_number;
        this.account_type = account_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCustomer_bank() {
        return customer_bank;
    }

    public void setCustomer_bank(String customer_bank) {
        this.customer_bank = customer_bank;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    @Override
    public String toString() {
        return "AccountData{" + "id=" + id + ", balance='" + balance + '\'' + ", customer_bank='" + customer_bank + '\'' + ", account_number='" + account_number + '\'' + ", account_type='" + account_type + '\'' + '}';
    }
}
