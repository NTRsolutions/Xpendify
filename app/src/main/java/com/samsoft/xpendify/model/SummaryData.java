package com.samsoft.xpendify.model;

/**
 * Created by Fred on 27-Aug-15.
 */
public class SummaryData {

    int id;
    String amount, category, date, note, color;

    public SummaryData() {
    }

    public SummaryData(int id, String amount, String category, String date, String note, String color) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.note = note;
        this.color = color;
    }

    public SummaryData(String amount, String category, String date, String note) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "SummaryData{" +
                "id=" + id +
                ", amount='" + amount + '\'' +
                ", category='" + category + '\'' +
                ", date='" + date + '\'' +
                ", note='" + note + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
