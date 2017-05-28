package com.samsoft.xpendify.model;

/**
 * Created by Fred on 26-Oct-15.
 */
public class BillsData {
    int id;
    String bill_category, bill_amount, due_date, note, status;

    public BillsData() {
    }

    public BillsData(int id, String bill_category, String bill_amount, String due_date, String note, String status) {
        this.id = id;
        this.bill_category = bill_category;
        this.bill_amount = bill_amount;
        this.due_date = due_date;
        this.note = note;
        this.status = status;
    }

    public BillsData(String bill_amount, String bill_category, String due_date, String note, String status) {
        this.bill_amount = bill_amount;
        this.bill_category = bill_category;
        this.due_date = due_date;
        this.note = note;
        this.status = status;
    }

    public String getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(String bill_amount) {
        this.bill_amount = bill_amount;
    }

    public String getBill_category() {
        return bill_category;
    }

    public void setBill_category(String bill_category) {
        this.bill_category = bill_category;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BillsData{" +
                "bill_amount='" + bill_amount + '\'' +
                ", id=" + id +
                ", bill_category='" + bill_category + '\'' +
                ", due_date='" + due_date + '\'' +
                ", note='" + note + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
